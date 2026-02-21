package com.gfpixel.gfpixeldungeon.utils;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.os.Environment;
import android.content.Intent;
import android.net.Uri;

import com.gfpixel.gfpixeldungeon.GirlsFrontlinePixelDungeon;
import com.gfpixel.gfpixeldungeon.windows.WndMessage;
import com.gfpixel.gfpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;


public class SaveDataManager {
    public static void exportSaveData() {
        try {
            // 1. 获取app内部存储路径
            File filesDir = GirlsFrontlinePixelDungeon.instance.getFilesDir();
            
            // 2. 获取Download目录
            File downloadDir = android.os.Environment.getExternalStoragePublicDirectory(
                android.os.Environment.DIRECTORY_DOWNLOADS
            );
            if (!downloadDir.exists()) {
                downloadDir.mkdirs();
            }
            
            // 3. 创建带时间戳的压缩包
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss", java.util.Locale.getDefault());
            String timestamp = sdf.format(new java.util.Date());
            String zipFileName = "GFPixelDungeon_Save_" + timestamp + ".zip";
            File zipFile = new File(downloadDir, zipFileName);
            
            // 4. 创建压缩包
            java.util.zip.ZipOutputStream zos = new java.util.zip.ZipOutputStream(
                new java.io.FileOutputStream(zipFile)
            );
            
            int copiedFiles = 0;
            
            // 导出全局文件
            copiedFiles += addFileToZip(zos, filesDir, "rankings.dat", "rankings.dat");
            copiedFiles += addFileToZip(zos, filesDir, "badges.dat", "badges.dat");
            copiedFiles += addFileToZip(zos, filesDir, "journal.dat", "journal.dat");
            copiedFiles += addFileToZip(zos, filesDir, "bones.dat", "bones.dat");
            
            // 导出所有游戏槽位的存档
            for (int slot = 1; slot <= com.gfpixel.gfpixeldungeon.GamesInProgress.MAX_SLOTS; slot++) {
                File gameFolder = new File(filesDir, "game" + slot);
                
                if (gameFolder.exists()) {
                    // 导出该槽位的game.dat
                    copiedFiles += addFileToZip(zos, gameFolder, "game.dat", "game" + slot + "/game.dat");
                    
                    // 导出该槽位的所有depth文件
                    for (int depth = 1; depth <= 50; depth++) {  // 最多检查50层
                        String depthFileName = "depth" + depth + ".dat";
                        File depthFile = new File(gameFolder, depthFileName);
                        
                        if (depthFile.exists()) {
                            copiedFiles += addFileToZip(zos, gameFolder, depthFileName, 
                                "game" + slot + "/" + depthFileName);
                        } else {
                            break;  // 如果不存在，说明没有更多层了
                        }
                    }
                }
            }
            
            zos.close();
            
            // 5. 显示成功消息
            if (copiedFiles > 0) {
                GirlsFrontlinePixelDungeon.scene().add(
                    new WndMessage("成功导出 " + copiedFiles + " 个文件\n" +
                                "位置: Download/" + zipFileName)
                );
            } else {
                if (zipFile.exists()) {
                    zipFile.delete();
                }
                GirlsFrontlinePixelDungeon.scene().add(
                    new WndMessage("没有找到可导出的存档文件")
                );
            }
            
        } catch (Exception e) {
            GirlsFrontlinePixelDungeon.reportException(e);
            GirlsFrontlinePixelDungeon.scene().add(
                new WndMessage("导出失败: " + e.getMessage())
            );
        }
    }

    //  辅助方法：添加文件到ZIP，返回是否成功（1或0）
    private static int addFileToZip(
        java.util.zip.ZipOutputStream zos, 
        File baseDir, 
        String fileName, 
    String zipEntryName) throws java.io.IOException {
        File file = new File(baseDir, fileName);
        
        if (!file.exists()) {
            return 0;
        }
        
        java.io.FileInputStream fis = new java.io.FileInputStream(file);
        
        try {
            java.util.zip.ZipEntry zipEntry = new java.util.zip.ZipEntry(zipEntryName);
            zos.putNextEntry(zipEntry);
            
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
            
            zos.closeEntry();
            return 1;
            
        } finally {
            fis.close();
        }
    }

    public static void importSaveData() {
        android.util.Log.d("SaveDataManager", "importSaveData called");
        
        GirlsFrontlinePixelDungeon.scene().add(
            new WndOptions(
                "导入存档",
                "这将覆盖当前所有存档！\n请确保已经备份。",
                "选择文件",
                "取消"
            ) {
                @Override
                protected void onSelect(int index) {
                    android.util.Log.d("SaveDataManager", "Dialog option selected: " + index);
                    if (index == 0) {
                        openFilePicker();
                    }
                }
            }
        );
    }
    
    // 打开文件选择器
    private static void openFilePicker() {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/zip");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            
            Intent chooserIntent = Intent.createChooser(intent, "选择存档压缩包");

            // 使用 GirlsFrontlinePixelDungeon.instance（它继承自 Game）
            GirlsFrontlinePixelDungeon.instance.startActivityForResult(
                chooserIntent, 
                1001  // REQUEST_IMPORT_SAVE
            );
            
        } catch (Exception e) {
            GirlsFrontlinePixelDungeon.reportException(e);
            GirlsFrontlinePixelDungeon.scene().add(
                new WndMessage("无法打开文件选择器: " + e.getMessage())
            );
        }
    }

    // 文件选择器返回后由 Activity 调用这个
    public static void onImportFileSelected(android.net.Uri uri) {
        try {
            java.io.InputStream inputStream = 
                GirlsFrontlinePixelDungeon.instance.getContentResolver().openInputStream(uri);
            if (inputStream == null) return;

            java.io.File filesDir = GirlsFrontlinePixelDungeon.instance.getFilesDir();
            java.util.zip.ZipInputStream zis = new java.util.zip.ZipInputStream(inputStream);
            java.util.zip.ZipEntry entry;
            int importedCount = 0;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".dat") && !entry.isDirectory()) {
                    java.io.File destFile = new java.io.File(filesDir, entry.getName());
                    java.io.File parentDir = destFile.getParentFile();
                    if (parentDir != null && !parentDir.exists()) parentDir.mkdirs();

                    java.io.FileOutputStream fos = new java.io.FileOutputStream(destFile);
                    byte[] buffer = new byte[4096];
                    int length;
                    while ((length = zis.read(buffer)) > 0) fos.write(buffer, 0, length);
                    fos.close();
                    importedCount++;
                }
                zis.closeEntry();
            }
            zis.close();
            inputStream.close();

            if (importedCount > 0) showImportSuccessAndRestart();

        } catch (Exception e) {
            GirlsFrontlinePixelDungeon.reportException(e);
            GirlsFrontlinePixelDungeon.scene().add(
                new WndMessage("导入失败: " + e.getMessage())
            );
        }
    }
    
    private static void showImportSuccessAndRestart() {
        // android.util.Log.d("GAME", "Import successful, restarting app");
        GirlsFrontlinePixelDungeon.scene().add(
            new WndMessage("导入成功即将重启")
        );

        try {
            // 延迟 1 秒后重启
            new java.util.Timer().schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    GirlsFrontlinePixelDungeon.restartApp();
                }
            }, 1000);
            
        } catch (Exception e) {
            android.util.Log.e("GAME", "Failed to restart", e);
        }
    }   
}