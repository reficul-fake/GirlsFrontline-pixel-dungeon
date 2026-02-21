/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2018 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.gfpixel.gfpixeldungeon.scenes;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Chrome;
import com.gfpixel.gfpixeldungeon.GirlsFrontlinePixelDungeon;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.armor.RangerArmor;
import com.gfpixel.gfpixeldungeon.items.armor.PlateArmor;
import com.gfpixel.gfpixeldungeon.items.armor.curses.Bulk;
import com.gfpixel.gfpixeldungeon.items.artifacts.TalismanOfForesight;
import com.gfpixel.gfpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.gfpixel.gfpixeldungeon.items.bags.MagicalHolster;
import com.gfpixel.gfpixeldungeon.items.bags.VelvetPouch;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfExperience;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfHealing;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfElements;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfEvasion;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfMight;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfSharpshooting;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfDisintegration;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfTransfusion;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.DMR.AK47;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.MG.M2HB;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.SMG.Lvoat1;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.SR.AWP;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.UG.C96;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.MG.Dp;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.AR.G36;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Launcher.Gepard;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.AR.Hk416;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.HB.Kriss;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.DMR.M16;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.SMG.M9;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.DMR.M99;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.MG.Mg42;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.LR.NAGANT;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.MG.Negev;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.SMG.Ump45;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.Shuriken;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.plants.Sungrass;
import com.gfpixel.gfpixeldungeon.sprites.CharSprite;
import com.gfpixel.gfpixeldungeon.sprites.ItemSprite;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.gfpixel.gfpixeldungeon.ui.Archs;
import com.gfpixel.gfpixeldungeon.ui.ExitButton;
import com.gfpixel.gfpixeldungeon.ui.Icons;
import com.gfpixel.gfpixeldungeon.ui.RenderedTextMultiline;
import com.gfpixel.gfpixeldungeon.ui.ScrollPane;
import com.gfpixel.gfpixeldungeon.ui.Window;
import com.gfpixel.gfpixeldungeon.windows.WndTitledMessage;
import com.watabou.input.Touchscreen;
import com.watabou.noosa.Camera;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TouchArea;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;

//TODO: update this class with relevant info as new versions come out.
public class ChangesScene extends PixelScene {

	private final ArrayList<ChangeInfo> infos = new ArrayList<>();

	@Override
	public void create() {
		super.create();

		int w = Camera.main.width;
		int h = Camera.main.height;

		RenderedText title = PixelScene.renderText( Messages.get(this, "title"), 9 );
		title.hardlight(Window.TITLE_COLOR);
		title.x = (w - title.width()) / 2f;
		title.y = (16 - title.baseLine()) / 2f;
		align(title);
		add(title);

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width() - TitleScene.PADDING, TitleScene.PADDING );
		add( btnExit );

		NinePatch panel = Chrome.get(Chrome.Type.TOAST);

		int pw = 135 + panel.marginLeft() + panel.marginRight() - 2;
		int ph = h - 16;

		panel.size( pw, ph );
		panel.x = (w - pw) / 2f;
		panel.y = title.y + title.height();
		align( panel );
		add( panel );

		ScrollPane list = new ScrollPane( new Component() ){

			@Override
			public void onClick(float x, float y) {
				for (ChangeInfo info : infos){
					if (info.onClick( x, y )){
						return;
					}
				}
			}

		};
		add( list );

		ChangeInfo changes = new ChangeInfo("v0.5.0", true, "");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes = new ChangeInfo(Messages.get(this, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		infos.add(changes);

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_GARNET, null), new RingOfMight().trueName(),
				"图形改进.\n\n" +
						"_-_ 采用了比原版更大的像素颗粒, 从而制作出更高分辨率的背景和角色.\n\n" +
						"_-_ 由于像素尺寸变大, 可能会出现动作轻微错位等错误.\n\n" +
						"_-_ 目前尚处于未完成状态, 计划将持续进行更新."));

		changes.addButton( new ChangeButton(new Lvoat1(),
				"_-_ 新增武器.\n\n" +
						"_-_ 添加了MG系列变种:M2HB.M2HB 拥有相对较高的命中率和接近 4 阶的超高伤害,且单次最高可攻击 5 次.\n\n" +
						"_-_ 但机枪系列无法进行偷袭的特性依然存在, 且每次使用 M2HB 时都会被施加流血和定身负面效果.流血效果可以叠加,这将给使用者带来巨大负担.\n\n" +
						"_-_ 添加了 3 种 LVOA 武器.作为 1 层隐藏武器,将 1 层武器投入转换之井时有概率获得.\n\n" +
						"_-_ LVOA type1 为基础型号,命中率 -20%, 单次最高可攻击 3 次.\n\n" +
						"_-_ LVOA type2 为 7.62mm 型号, 命中率 +20%, 且每级强化可获得高达 5 点的伤害增幅.\n\n" +
						"_-_ LVOA type3 为短枪管型号, 命中率 -60%, 奇袭时造成 50% 额外伤害, 单次最高可攻击 3 次."));



		changes.addButton( new ChangeButton(new Image(Assets.GOLYATPLUS, 0, 0, 26, 23), "变化",
				"请不要招惹他.\n\n" +
						"_-_ 击败第三区boss时, 有极低概率掉落升级磁盘.\n\n" +
						"_-_ 堤丰的近战伤害提升至 85, 且发射激光后不再消耗额外时间, 将立即准备下次射击."));

		changes = new ChangeInfo(Messages.get(this, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		infos.add(changes);

		changes.addButton( new ChangeButton(new AWP(),
				"_-_ 武器调整.\n\n" +
						"_-_ RF系列攻击速度已调整.\n\n" +
						"_-_ 春田的攻击速度调整为3回合, AWP调整为4回合, 但春田和AWP在攻击后, 将分别进入持续6回合和7回合的混乱状态, 期间无法使用武器.\n\n" +
						"_-_ NTW的命中率上调至500%, 攻击速度减少至5回合, 且攻击后会进入持续8回合的混乱状态.\n\n" +
						"_-_ Vector 攻击速度提升了50%, 增益持续时间增加了0.5回合."));

		changes = new ChangeInfo(Messages.get(this, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new AWP(),
				"_-_ 武器调整.\n\n" +
						"_-_ RF系列攻击速度已调整.\n\n" +
						"_-_ 春田的攻击速度调整为3回合, AWP调整为4回合, 但春田和AWP在攻击后, 将分别进入持续6回合和7回合的混乱状态, 期间无法使用武器.\n\n" +
						"_-_ NTW的命中率上调至500%, 攻击速度减少至5回合, 且攻击后会进入持续8回合的混乱状态.\n\n" +
						"_-_ Vector 攻击速度提升了50%, 增益持续时间增加了0.5回合."));

		changes = new ChangeInfo(Messages.get(this, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.NEMEUM, 0, 0, 26, 21), "削弱",
				"装甲系怪物削弱.\n\n" +
						"_-_ 残兽的攻击力降低了3点, 攻击速度变为原来的两倍. 此外, 可疑肉块的掉落率上调至20%.\n\n" +
						"_-_ 残兽和海德拉Hydra的移动速度降低了50%. 此外, 残兽的攻击力下调至15~20, 并改为每回合攻击两次."));

		changes = new ChangeInfo("v0.4.9", true, "");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes = new ChangeInfo(Messages.get(this, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		infos.add(changes);

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_GARNET, null), new RingOfMight().trueName(),
				"图形优化.\n\n" +
						"_-_ 采用了比原先更大的像素点，以此制作出更高分辨率的背景与角色.\n\n" +
						"_-_ 由于像素点较原先变大，可能会出现动作轻微错位等错误.\n\n" +
						"_-_ 目前背景尚处于未完成状态，后续将持续进行更新."));

		changes.addButton( new ChangeButton(new Image(Assets.GOLYATPLUS, 0, 0, 26, 23), "变种",
				"新增分支点变种怪物.\n\n" +
						"_-_ 出现在分支点的歌利亚已更换为歌利亚 PLUS.\n\n" +
						"_-_ 歌利亚 PLUS 拥有与原先黑色外形不同的红色外形, 且生命值与攻击力极低.\n\n" +
						"_-_ 但是, 歌利亚 PLUS 的攻击必定命中, 且在被击杀时会对周围造成高额的自爆伤害."));

		changes = new ChangeInfo(Messages.get(this, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Negev(),
				"_-_ 武器调整.\n\n" +
						"_-_ MG系列命中率调整为 90%.但 MG4 调整为 75%.\n\n" +
						"_-_ Vector 的基础伤害和强化效率提升了 2 点. 此外, 移除了命中惩罚,Buff 持续时间也小幅增加.\n\n" +
						"_-_ DMR 速射系列(Kar98k、SVD、Super SASS)的攻击速度全部统一为 +20%.\n\n" +
						"_-_ 使用 C96 命中时, 将获得持续 10 回合的照明增益.\n\n" +
						"_-_ H&K MP7 的命中率调整到了 65%. MP7 是一款足够强大的武器, 但其作为 1 阶的局限性非常明显, 在面对高防御目标时几乎无法造成伤害, 因此稍微减轻了其惩罚属性."));

		changes = new ChangeInfo(Messages.get(this, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.WARRIOR, 0, 69, 21, 23),  "小幅加强",
				"临时补丁.\n\n" +
						"_-_ 当 UMP45 进食时, 除了恢复 5% HP 外, 还会进入持续 3 轮的隐身状态."));

		changes = new ChangeInfo(Messages.get(this, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.M4A1, 0, 0, 17, 20), "数值调整",
				"M4A1 相关数值调整.\n\n" +
						"_-_ M4A1 的伤害上限从 30 调整至 100.\n\n" +
						"_-_ 肉桂卷的生命值减至 5, 但攻击力提升至 25~40."));

		changes.addButton( new ChangeButton(new Image(Assets.NEMEUM, 0, 0, 26, 21), "削弱",
				"装甲类怪物下调.\n\n" +
						"_-_ 钢狮 攻击力固定为 40, 删除了伤害上限, 且发射激光所需的时间调整为 4 回合.\n\n" +
						"_-_ 九头蛇的生命值从 400 下调至 200, 近战和远程攻击伤害均更改为 22, 激光蓄力时间改为 4 回合, 并新增了 3 回合的激光冷却时间. 此外，近战攻击将适用 2 连击.\n\n" +
						"_-_ 木星发射激光所需时间增加 1 回合, 变为 6 回合, 激光伤害固定为 70; 像其他装甲单位一样删除了防御力, 作为补偿, 生命值增加到 150.\n\n" +
						"_-_ 堤丰的生命值更改为 4500, 移动速度 80%, 最高等级 50. 攻击速度为 8 回合, 激光伤害最高 135, 并更改为对伞免疫.\n\n\n" +
						"_-_ 19 层的蝎甲兽生成率大幅降低, 攻击力降至 35, 伤害限制降至 50, 生命值降至 150, 使其回归最初的设计目标: 仅作为对 SR 系列武器的制衡."));

		changes = new ChangeInfo("v0.4.7", true, "");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes = new ChangeInfo(Messages.get(this, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		infos.add(changes);

		changes.addButton( new ChangeButton(new M9(),
				"武器变更.\n\n" +

						"_-_ HHK416的默认武器从 Jericho 变更为 MP7. \n\n" +
						"_-_ MP7 虽然比 MG系列慢, 但拥有极快的攻击速度和相对较高的攻击力, 以及较低的命中率."));


		changes = new ChangeInfo(Messages.get(this, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(this, "bugfixes"),
				"버그 수정\n\n" +

						"_-_ 修正了一些错别字。让我们做一个严格遵守拼写规范的好孩子吧\n\n" +
						"_-_ 猎鸥C型(分支点)伤害过高的错误.\n\n" +
						"  - 下调了猎鸥C型的虚弱减益持续时间.\n\n" +
						"_-_ 修复了Jaguar攻击触发时会导致游戏崩溃的错误.\n\n" +
						"_-_ 修复了艾尔菲特在睡眠状态下损失超过一半体力时, 会从第1阶段而非第2阶段开始的错误. \n\n" +
						"_-_ 修复了艾尔菲特在第2阶段释放冲锋和Magnum Wedding后会丢失玩家目标的错误. \n\n" +
						"_-_ 修复了艾尔菲特向靠墙的敌人冲锋时会导致游戏崩溃的错误. \n\n" +
						"_-_ 修复了在回到地面的结局动画中，出现的角色与所选角色不一致的错误. \n\n" +
						"_-_ 修复了与破坏者等遭遇时, 台词无法正常显示的错误. \n\n" +
						"_-_ 修复了艾尔菲特在睡眠状态下受到Satellite攻击时, 无法进入下一阶段的错误."));

		changes.addButton( new ChangeButton(new Negev(),
				"_-_ 武器调整.\n\n" +
						"_-_ MG系列武器删除了偷袭时的伤害削减, 取而代之的是变更为无法偷袭, 且命中率提升了20%.\n\n" +
						"_-_ 为避免与其他道具混淆, 装备校准券更名为外骨骼校准券.\n\n" +
						"_-_ 种子袋更名. 除了种子外, 现在还可以额外收纳外骨骼校准券, 高级装备校准券, 特性装备校准券以及快速修复契约."));

		changes.addButton( new ChangeButton(new VelvetPouch(),
				"_-_ 道具调整.\n\n" +
						"_-_ 高级装备校准券更名, 可同时用于武器和外骨骼, 用于外骨骼时会牺牲防御力或回避力中的一项来强化另一项.\n\n" +
						"_-_ 为避免与其他道具混淆, 装备校准券更名为外骨骼校准券.\n\n" +
						"_-_ 种子袋更名. 除了种子外, 现在还可以额外收纳外骨骼校准券, 高级装备校准券, 特性装备校准券以及快速修复契约."));

		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "개발 관련",
				"_-_ \"系统变更\n\n" +

						"_-_ 根据地下城的深度, 意外发现秘密门和隐藏陷阱的概率已完成调整.\n\n\n" +
						"_-_ 修复了麻痹药水在地图掉落时处于已鉴定状态的错误.\n\n\n" +
						"_-_ 更改为击败战术人形春田时掉落的麻痹药水将自动处于已鉴定状态."));

		changes = new ChangeInfo(Messages.get(this, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.NEMEUM, 0, 0, 26, 21), "加强",
				"部分怪物强化.\n\n" +
						"_-_ 切割者的生命值从 15 强化至 30, 且当生命值较低时, 自动回避率将大幅提升.\n\n" +
						"_-_ 蝎狮兽的攻击力大幅上调."));

		changes.addButton( new ChangeButton(new M16(),
				"道具调整.\n\n" +
						"_-_ M16A1 恢复了偷袭攻击能力, 且伤害小幅提升.\n\n" +
						"_-_ 发射器类（.50口径强化武器）武器移除了缓慢的攻速限制, 现在拥有平均水平的攻击速度.\n\n" +
						"_-_ 力量药水的额外生命值提升效果增加了 5 HP.\n\n" +
						"_-_ 密斯·特拉维亚Miss Travia更名为塞浦路斯Cyprus, 现在可以切换武器模式并可以使用武器固有技能."));

		changes = new ChangeInfo(Messages.get(this, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Kriss(),
				"Vector 整体遭到了下调.\n\n" +
						"_-_ 现在已无法进行偷袭.\n\n" +
						"_-_ 命中时触发的时间增幅 Buff 持续时间已缩短.\n\n" +
						"_-_ 攻击速度降低至 1, 且命中率降低了 50%."));


		changes = new ChangeInfo("v0.4.6", true, "");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes = new ChangeInfo(Messages.get(this, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		infos.add(changes);


		changes.addButton( new ChangeButton(new Image(Assets.ELPHELT, 69, 0, 23, 24), "分支",
				"_-_ 激活 1号分支. \n\n" +
						"_-_ 少女前线&罪恶装备&苍翼默示录联动分支章节已激活.\n\n" +
						"_-_ 原有关卡中满足特定条件时, 将在下一章节激活分支章节.\n\n" +
						"_-_ 通关分支章节后, 可以获得性能优于现有等级的联动武器. \n\n" +
						"_-_ 分支的激活条件暂不公开.但请放心, 它们很容易找到! \n\n" +
						"_-_ 目前 Boss AI 仍存在未修复的 Bug.我们计划在下个版本中进行修正, 敬请谅解."));


		changes.addButton( new ChangeButton(new Image(Assets.GAGER, 0, 0, 24, 18), "新 Boss",
				"_-_ 第3章 Boss 变更. \n\n" +
						"_-_ 由于蝎尾兽被改为野外怪物, 第三章新增了一个 Boss.\n\n" +
						"_-_ 计量官拥有与蝎尾兽不同的攻击模式, 由于目前仍处于未完成状态, 后续将添加更多新技能."));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARMOR_CLOTH, new Bulk().glowing()), "新病毒",//이부분을 저주함정이나 저주받은 외골격 이미지로 바꿔주세요
				"_-_ 添加了多种类型的病毒.\n\n" +
						"_-_ 干扰 - 武器病毒.攻击时偶尔会同时干扰敌人和自己.\n\n" +
						"_-_ 弹性 - 武器病毒. 伤害变为 0, 但会击退被击中的敌人.\n\n" +
						"_-_ 臃肿 - 护甲病毒. 增加通过门所需的回合数.\n\n" +
						"_-_ 丛生 - 护甲病毒. 受到攻击时, 偶尔会触发随机种子效果."));

		changes.addButton( new ChangeButton(new WandOfDisintegration(),
				"_-_ 装甲属性已定义.\n\n" +
						"_-_ 装甲属性敌人会受到穿甲弹 3 倍的伤害.\n\n" +
						"_-_ 装甲属性敌人拥有强大的攻击力和高生命值,但移动速度普遍较慢,由于防御力较低或没有防御力,攻击更容易命中并能造成高额伤害."));

		changes.addButton( new ChangeButton(new Gepard(),
				"_-_ 修改了部分武器.\n\n" +
						"_-_ 添加了新武器 猎豹Gepard和 SRS. 装备猎豹时, .50口径子弹的伤害将大幅提升.\n\n" +
						"_-_ 删除了燃烧弹、EMP弹和榴弹, 合并为名为.50口径的物品. 被删除的 EMP弹和燃烧弹可以通过大锅组合 .50口径子弹与种子来制作.\n\n" +
						"_-_ PG 毒气弹更名为坍塌液。现在发射的是坍塌废气而非毒气，暴露在坍塌废气中会受到类似于酸性效果的伤害.\n\n" +
						"_-_ UMP45 和 M16A1 的伤害小幅提升.M16A1 删除了无法偷袭和攻击速度慢的负面属性,并小幅提升了强化和防御收益."));

		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "开发相关",
				"_-_ \"系统变更\n\n" +

						"_-_ UI 进行了大幅优化！我们努力打造了更贴近少女前线风格的界面.\n\n" +
						"_-_ 添加了不掉落力量药水的挑战模式.\n\n" +
						"_-_ 添加了新的成就勋章.\n\n" +
						"_-_ 投掷武器系统重制. 添加了多种投掷武器, 并引入了耐久度系统.\n\n" +
						"_-_ 在有食人鱼的房间铺设了一块地砖, 以减少因误入而受到伤害的情况. \n\n" +
						"_-_ 存档栏位增加至 10 个."));

		changes = new ChangeInfo(Messages.get(this, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		infos.add(changes);

		changes.addButton( new ChangeButton(new PlateArmor(),
				"_-_ 装备特质已更改.\n\n" +
						"_-_ 赌运: 造成2倍伤害的概率从60%降低至50%, 但如果造成了0倍伤害, 下一次攻击造成2倍伤害的概率将会增加.\n\n" +
						"_-_ 晦暗- 移除了防御力惩罚, 并降低了隐藏效果\n\n" +
						"_-_ 缠绕: 提供的自然护甲值增加, 强化等级越高, 束缚时间越短\n\n" +
						"_-_ 电势: 删除了自伤效果，并增加了子弹充能次数\n\n" +
						"_-_ 重甲: 闪避率变为0, 作为补偿, 将根据闪避率按比例增加防御力\n\n" +
						"_-_ 迅捷: 移除了防御力惩罚, 仅在周围没有敌人时提供移速加成\n\n" +
						"_-_ 粘性: 出现概率增加, 重甲的出现概率降低"));

		changes.addButton( new ChangeButton(new MagicalHolster(),
				"_-_ IOP大容量弹匣现在可以存放投掷武器\n\n" +
						"_-_ 存放在弹匣中的投掷武器将获得额外20%的耐久度."));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_RUBY, null), new RingOfSharpshooting().trueName(),
				"狙击红点准镜变更\n\n" +
						"_-_ 移除了投掷攻击的额外命中率\n\n" +
						"_-_ 移除了投掷武器不被消耗的概率, 改为增加投掷武器的耐久度\n\n" +
						"_-_ 新增根据强化等级按比例增加投掷武器伤害的效果"));

		changes.addButton( new ChangeButton(new Image(Assets.NEMEUM, 0, 0, 26, 21), "변경",
				"装甲型怪物已重制.\n\n" +
						"_-_ 下说明中出现的所有怪物均被赋予装甲属性.\n\n" +
						"_-_ 钢狮H型: 现在出现在第3阶,在短时间装填后,会发射无视地形及外骨骼防御率的崩坏光束.\n\n" +
						"_-_ 蝎甲兽: 现在作为第4阶的野外怪物出现. 蝎甲兽虽然没有防御力且移动稍慢, 但拥有极高血量, 并且受到的单次伤害不会超过指定上限.\n\n" +
						"_-_ 木星：删除了装填中的伤害减免,血量调整至原先的两倍110.\n\n" +
						"_-_ 量产型提丰: 血量增加至3500, 移动速度加快20%, 因其处于悬浮状态, 不会触发陷阱. 此外, 它免疫麻痹和恐惧, 并对即死, 毒气, 血饮, 自爆磁盘具有抗性."));

		changes = new ChangeInfo(Messages.get(this, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton( new Image(Assets.RANGER, 0, 69, 21, 23), "加强",
				"HK416 加强.\n\n" +
						"_-_ 所有投掷武器的耐久度额外增加 50%\n\n" +
						"_-_ M79 榴弹发射器的机制已更改,现在即便击中墙壁而非敌人也会产生爆炸,从而造成溅射伤害.\n\n" +
						"IOP 弹匣提供的 20% 投掷武器耐久度加成将与基础能力以乘法叠加,最终获得 80% 的额外耐久度."));

		changes.addButton( new ChangeButton( new Image(Assets.MAGE, 0, 69, 21, 23), "重做",
				"G11 外骨骼重做.\n\n" +
						"_-_ G11 的外骨骼技能由主动技能改为增益(Buff)技能。请务必仅在生命值为 100% 时使用.\n\n" +
						"_-_ G11 的外骨骼技能不再是将周围敌人变为烧伤状态的鸡肋能力，而是获得名为“加速”的增益效果.\n\n" +
						"加速虽然会消耗 G11 近乎致命的 90% 生命值,但在 4 个回合内,所有与时间相关的能力都会得到增强.包括攻击速度,移动速度以及包括加速在内的所有增益持续时间都会暂时增加,使其能在短时间内展开强大且多样的战术."));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_OPAL, null), new RingOfElements().trueName(),
				"元素瞄准镜 加强\n\n" +
						"_-_ 相比以前, 可以应对更多的元素/远程效果\n\n" +
						"_-_ 高强化等级下, 虚弱效果的持续时间和伤害量较以往大幅减少\n\n" +
						"_-_ 现在赋予的效果不再是概率抵抗元素/魔法伤害, 而是根据强化等级成比例降低所受的元素/魔法伤害"));

		changes.addButton( new ChangeButton(new TimekeepersHourglass (),
				"_-_ 怀表 加强.\n\n" +
						"_-_ 充能数需求减少一半\n\n" +
						"_-_ 每 1 点充能使周围时间停止的效果提升至 2 倍\n\n" +
						"_-_ 低强化等级下的充能速度增加，达到 +10 时与此前持平"));

		changes.addButton( new ChangeButton(new TalismanOfForesight(),
				"_-_ 眼镜史诗级加强.\n\n" +
						"_-_ 在 +0 时充能速度增加 20%, 随强化等级提升充能速度加成比以往更高, 最终在 +10 时增加 50%\n\n" +
						"_-_ 通过发现陷阱获得的额外充能加成保持不变"));

		changes = new ChangeInfo(Messages.get(this, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.WARRIOR, 0, 69, 21, 23), "削弱",
				"斗神调整与削弱\n\n" +
						"_-_ 触发狂暴所需的等级从 2 级提高至 3 级\n\n" +
						"_-_ 每次受到物理伤害时会积攒愤怒值Buff,根据该 Buff 的数值，自身的物理攻击力最高可提升 50%.该 Buff 会随时间流逝而减少,但生命值越低,减少速度越慢\n\n" +
						"_-_ 触发狂暴状态必须拥有 100% 的愤怒值，且在该状态下受到的额外伤害降低 50%.\n\n" +
						"_-_ 在触发狂暴后的恢复期间,无法积攒愤怒值."));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_EMERALD, null), new RingOfEvasion().trueName(),
				"闪避瞄准镜削弱\n\n" +
						"_-_ 不再降低被敌人发现的概率\n\n" +
						"_-_ 降低了效果叠加的程度 —— 计算方式从乘法运算改为加法运算."));

		changes.addButton( new ChangeButton(new RangerArmor(),
				"_-_ UMP9 & HK416 的外骨骼下调.\n\n" +
						"_-_ UMP9 - 烟雾弹范围限制为 8 格，且效果调整为不再穿透墙壁\n\n" +
						"_-_ HK416 - 杀伤榴弹的范围限制为 12 格"));

		changes.addButton( new ChangeButton(new Negev(),
				"_-_ MG系列削弱.\n\n" +
						"_-_ 调整了 MG 系列(基础攻击次数大于5)武器的命中率和偷袭效果.\n\n" +
						"_-_ 命中率将受到最高 40% 的惩罚,虽然基础攻击力保持不变,但进行偷袭时只能造成 60% 的伤害."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.POTION_MAGENTA, null), new PotionOfHealing().trueName(),
				"_-_ 治疗药水掉落率下调.\n\n" +
						"_-_ 从掉落治疗药水的怪物处获得药水后，后续从同种怪物处获得治疗药水的概率会降低"));

		changes.addButton( new ChangeButton(new Sungrass.Seed(),
				"_-_ 阳春草 & 地缚根 - 하향.\n\n" +
						"_-_ 大幅降低阳春草的恢复速度,等级越高降低越多,最高降低至原恢复速度的 40%.\n\n" +
						"_-_ 在接受阳春草恢复期间受到攻击，恢复量不再减少.\n\n" +
						"_-_ 在接受阳春草恢复期间即使生命值已满,效果也不会立即结束.\n\n" +
						"_-_ 地缚根的伤害吸收比例从原来的 50% 调整为与楼层数成比例"));

		changes = new ChangeInfo("v0.4.0b3e1", true, "快速修复");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(this, "bugfixes"),
				"修复致命漏洞\n\n" +

						"_-_ 修复了在17层以后有概率导致游戏强制关闭并同时造成存档损坏的漏洞.\n\n" +
						"_-_ 木星炮, 九头蛇, 以及提丰量产型在激光蓄力期间的减伤倍率从5倍降低至2倍.作为补偿,小幅上调了木星炮和九头蛇的生命值.\n\n" +
						"_-_ UMP45的基础武器伤害提升了2点。删除了偷袭额外伤害,低命中率和长射程属性,取而代之的是获得了较低的伤害吸收能力. \n\n" +
						"_-_ UMP9的基础武器伤害下调了2点。考虑到其已经拥有足够高的偷袭伤害,我认为这次削弱是合理的. \n\n" +
						"_-_ 重新调整了6阶武器的掉落概率与性能.由于其掉落率远超预期,现已将其调整至与5级武器相近的水平."));


		changes = new ChangeInfo("v0.4.0b3", true, "");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes = new ChangeInfo(Messages.get(this, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Dp(),
				"新增了2种武器!\n\n" +
						"_-_ 增加了1阶隐藏武器和5阶的新武器!"));

		changes.addButton( new ChangeButton(new Image(Assets.DISLOLI, 0, 0, 19, 19), "追加",
				"新增了Boss, 稀有怪物以及新主题.\n\n" +
						"_-_ 对25层的Boss进行了微调, 并增加了30层区域的稀有怪物和25层的新主题. "));


		changes = new ChangeInfo(Messages.get(this, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(this, "bugfixes"),
				"Fixed:\n" +
						"_-_ 解决了文本输出问题." ));


		changes.addButton( new ChangeButton(new Image(Assets.STAR, 0, 0, 21, 20), "开发人员变动",
				"_-_ 原开发者 Kirsi 已更换为新开发者 Sharku.感谢 Kirsi 在基础开发阶段的辛勤付出! " ));


		changes.addButton( new ChangeButton(new Ump45(),
				"_-_ 部分武器已进行修改.\n\n" +
						"_-_ UMP45的默认武器已更改.请亲自确认吧!\n\n" +
						"_-_ 双管锯短散弹枪已更换为 M16A1. M16A1 具有类似防弹板的效果, 可以进行远程攻击, 但攻速稍慢且命中率较低."));

		changes = new ChangeInfo(Messages.get(this, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new M99(),
				"M99 再调整.\n\n" +
						"M99 的高破坏力固然充满魅力，但无法偷袭和低命中率是过于严苛的负面属性.此前削弱该武器的原因是其与暗杀者的协同效应过强。\n 因此，在保留无法偷袭属性的同时, 我们大幅提升了命中率, 旨在使其在不依赖暗杀者职业的情况下也能发挥作用."));

		changes = new ChangeInfo(Messages.get(this, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.ACYCLOPS, 0, 0, 20, 20), "너프",
				"降低了部分敌人的体力.\n\n" +
						"_-_ 九头蛇的体力从 360 削弱至 190.\n\n" +
						"_-_ 盾兵的体力有所增加,但移动速度,攻击力和防御力均有所下降."));

		changes = new ChangeInfo("v0.4.0b2e1", true, "紧急修复");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes.addButton( new ChangeButton( new C96(),
				"隐藏武器紧急调整\n\n" +

						"_-_ 我们决定将原本在地图中掉落的两款隐藏武器转为常规武器.不过,目前仍保留了另外两款隐藏武器,这两款武器无法通过常规手段获得.\n\n" +
						"_-_ 转为常规化的武器为 C96 和 G36, 我们大幅削减了它们的伤害, 但保留了部分武器特性.\n\n" +
						"_-_ G36 从3阶降至2阶, 移除了超远射程和极快的攻速.不过,它现在拥有较高的强化收益(虽不及此前)和略快的攻速.\n\n" +
						"_-_ C96 从2阶升至3阶, 移除强化收益且基础伤害遭到大幅削弱, 但新增了长射程和偷袭伤害加成. "));

		changes = new ChangeInfo("v0.4.0b2", true, "");
		changes.hardlight(Window.TITLE_COLOR);
		infos.add(changes);

		changes = new ChangeInfo(Messages.get(this, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		infos.add(changes);

		changes.addButton( new ChangeButton(new G36(),
				"新增了2种隐藏武器!\n\n" +
						"_-_ 隐藏武器拥有比同阶级武器更优越的性能,且无法通过普通手段获得.祝你好运!我是不会告诉你怎么拿到的!"));

		changes.addButton( new ChangeButton(new ThrowingKnife(),
				"添加了一些尚不稳定的投掷类武器.\n\n" +
						"_-_ 它们很有可能无法像说明中那样正常工作.抱歉!我终究只是个业余选手!"));


		changes = new ChangeInfo(Messages.get(this, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(this, "bugfixes"),
				"Fixed:\n" +
						"_-_ 文本显示异常已修复." ));


		changes.addButton( new ChangeButton(new MagicalHolster(),
				"_-_ 降低了前期难度!\n\n" +
						"_-_ 为所有 4 名角色各额外添加了两件初始道具.\n\n" +
						"_-_ UMP45 初始携带药水匣与修复药水, UMP9 初始携带磁盘盒与隐身药水.\n\n" +
						"_-_ 此外, G11 初始携带弹匣与高速装填磁盘, HK416 初始携带种子袋."));


		changes.addButton( new ChangeButton(new NAGANT(),
				"_-_ 确认到6阶物品已在野外掉落!\n\n" +
						"_-_ 确认 6阶物品可在野外掉落后, 我们已对掉落率进行了调整.\n\n" +
						"_-_ 6阶物品的掉落概率会比 5阶稍微高一些, 同时也提高了武力瞄准镜的掉落概率。该补丁内容在后续可能会进行修改."));

		changes = new ChangeInfo(Messages.get(this, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton( new Image(Assets.RANGER, 0, 69, 21, 23),"榴弹兵加强",
				"提高了榴弹兵的榴弹倍率.\n\n" +
						"为了让榴弹在常规战斗中更易于使用, 将原有的充能倍率从 7% 提高到了 10%."));

		changes = new ChangeInfo(Messages.get(this, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.DRAGUN, 0, 0, 21, 27), "削弱",
				"降低了部分敌人的生命值.\n\n" +
						"_-_ 龙骑兵生命值调整为 30。虽然仍保持高回避率和高攻击力, 但现在只要命中一次, 就有可能将其击杀.\n\n" +
						"_-_ 木星炮Jupiter装填时间增加了1回合, 且被伞病毒影响的概率大幅提升.不过, 击败它获得的经验值减少到了 1\n\n" +
						"_-_ 九头蛇Hydra和独眼巨人Cyclops生命值分别降低了40. 调整后九头蛇的生命值为360, 独眼巨人为 120"));

		//**********************
		//       v0.4.X
		//**********************

		changes = new ChangeInfo("v0.4.0b1", true, "");
		changes.hardlight(Window.TITLE_COLOR);
		infos.add(changes);

		changes = new ChangeInfo(Messages.get(this, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		infos.add(changes);

		changes.addButton( new ChangeButton( Icons.get(Icons.DEPTH), "地牢改版!",
				"更多的挑战层数与全新的叙事体验现已上线!\n\n" +
						"_-_ 新增 23~31 层供大家探索.\n\n" +
						"_-_ 共有 6 种新怪物加入战斗.\n\n" +
						"_-_ 改为对话交互模式, 大幅提升剧情代入感.\n\n" +
						"更新仍在继续.关于怪物数值平衡或玩法建议, 欢迎前往 Twitter 或 DCInside 发布贴留言, 感谢大家的支持."));

		changes.addButton( new ChangeButton( new Image(Assets.RANGER, 0, 69, 21, 23), "HK416 的转职已发生改变!",
				"HK416 的转职已经进行了调整!\n\n" +
						"原有的特种兵Commando现已更改为榴弹兵.\n" +
						"_-_ HK416 现在开局自带 M79 榴弹发射器. \n" +
						"_-_ 榴弹兵每次使用斧头攻击敌人时，都会为榴弹提供额外的伤害倍率加成. \n\n" +
						"详细信息请参考 DCInside 上的说明贴. "));

		changes.addButton( new ChangeButton(new Mg42(),
				"_-_ 第4阶和第6阶武器已添加!\n\n" +
						"_-_ 开发过程中, 目前尚未确认这些武器在地图场景中掉落.\n\n" +
						"_-_ 经确认, 4、6阶武器目前不会在场景中掉落, 唯一的获取方式是通过 ST-AR15 的任务获得。\n\n" +
						"_-_ 如果您获取到了第6阶武器, 请通过作者的 Twitter告知获取途径.\n\n" +
						"_-_ Twitter ID 是 @_NamSek.期待各位玩家的反馈, 谢谢."));


		changes = new ChangeInfo(Messages.get(this, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		infos.add(changes);

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_GARNET, null), new RingOfMight().trueName(),
				"_-_ 许多物品的贴图已经进行了修改!\n\n" +
						"_-_ -原有的瞄准镜贴图得到了画质提升, 并统一更换为 全息, 普通红点, Aimpoint红点 三种样式" +
						"_-_ -卷轴类物品的外观与说明已改为微型芯片形式.\n\n" +
						"_-_ 部分武器, 消耗品与遗物的贴图也已更新. 详细内容请参考发布站点的补丁说明."));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.POTION_GOLDEN, null), new PotionOfExperience().trueName(),
				"_-_ 玩家等级上限已扩展至40级!\n\n" +
						"_-_ 而且, 出现0伤害(攻击无效)的概率下调了 10%!"));

		changes.addButton( new ChangeButton(new Hk416(),
				"_-_ 调整了许多武器的特性.\n\n" +
						"_-_ 散弹枪系列武器的命中率增加了15%. \n\n" +
						"_-_ 春田步枪伤害翻倍, 且射程与攻击间隔分别修改为50格, 5回合.\n\n" +
						"_-_ Kar98k, SVD, S.SASS的高命中特性被删除, 增加了2格射程与50%的攻击速度." +
						"_-_ 内盖夫攻击速度修改为1回合5次, 且伤害被调整至低于1阶水平."));

		changes = new ChangeInfo(Messages.get(this, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new AK47(),
				"AK-47射程增加1格\n\n" +
						"_-_ AK-47虽然威力足够, 但低命中率的惩罚一直是其短板. 因此, 我们决定赋予AK-47额外1格的先手攻击机会, 以增加该武器的选择价值."));

		changes.addButton( new ChangeButton(new Shuriken(),
				"投掷类武器伤害调整\n\n" +
						"_-_ 此前, 由于榴弹和回旋榴弹的最低伤害过低, 经常无法造成有效打击. 因此, 我们分别小幅提升了它们的最低伤害, 使其能够造成更有效的输出."));

		changes.addButton( new ChangeButton(new WandOfTransfusion(),
				"添加了DR注魂弹相性的敌人!\n\n" +
						"_-_ 一直以来, DR注魂弹在高级补给箱中都是鸡肋的存在。为了稍微增加该子弹的用途, 我对DR注魂弹添加了对精英敌人的增伤, 从而间接加强了DR注魂弹."));
		changes = new ChangeInfo(Messages.get(this, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new M99(),
				"移除了M99的攻击延迟, 新增命中率下降10%及无法奇袭的惩罚：\n\n" +
						"_-_ 相比其负面效果, M99的优势过于强大. 虽然移除了M99较慢的攻击速度限制, 但我们决定加入无法奇袭和-10%命中率的惩罚."));

		//**********************
		//       v0.3.X
		//**********************

		changes = new ChangeInfo( "v0.3.0", true, "更多信息即将在此更新.");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "开发相关",
				"_-_ 2017-10-26 22:11:55. 基于 Shattered Pixel Dungeon v0.6.2 版本开始开发"));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_GARNET, null), new RingOfMight().trueName(),
				"部分道具的贴图已完成修正." ));

		//**********************
		//       v0.2.X
		//**********************

		changes = new ChangeInfo( "v0.2.0", true, "更多信息即将添加到此处.");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(this, "bugfixes"),
				"Fixed:\n" +
						"-_- 已将包名与 Shattered Pixel Dungeon 独立, 现在可以分开安装了." ));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_GARNET, null), new RingOfMight().trueName(),
				"修改了部分道具的贴图." ));

		//**********************
		//       v0.1.X
		//**********************

		changes = new ChangeInfo( "v0.1.0", true, "更多信息稍后将在此更新.");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "开发相关",
				"-_- \"2017-09-24 21:22:09. 基于 Shattered Pixel Dungeon v0.6.1 开始开发\n\\n" +
						"-_- 加油! 少前地牢冲啊!"));


		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_GARNET, null), new RingOfMight().trueName(),
				"修改了部分道具的材质贴图." ));
		Component content = list.content();
		content.clear();

		float posY = 0;
		float nextPosY = 0;
		boolean second =false;
		for (ChangeInfo info : infos){
			if (info.major) {
				posY = nextPosY;
				second = false;
				info.setRect(0, posY, panel.innerWidth(), 0);
				content.add(info);
				posY = nextPosY = info.bottom();
			} else {
				if (!second){
					second = true;
					info.setRect(0, posY, panel.innerWidth()/2f, 0);
					content.add(info);
					nextPosY = info.bottom();
				} else {
					second = false;
					info.setRect(panel.innerWidth()/2f, posY, panel.innerWidth()/2f, 0);
					content.add(info);
					nextPosY = Math.max(info.bottom(), nextPosY);
					posY = nextPosY;
				}
			}
		}


		content.setSize( panel.innerWidth(), (int)Math.ceil(posY) );

		list.setRect(
				panel.x + panel.marginLeft(),
				panel.y + panel.marginTop() - 1,
				panel.innerWidth(),
				panel.innerHeight() + 2);
		list.scrollTo(0, 0);

		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );

		fadeIn();
	}

	@Override
	protected void onBackPressed() {
		GirlsFrontlinePixelDungeon.switchNoFade(TitleScene.class);
	}

	private static class ChangeInfo extends Component {

		protected ColorBlock line;

		private RenderedText title;
		private boolean major;

		private RenderedTextMultiline text;

		private ArrayList<ChangeButton> buttons = new ArrayList<>();

		public ChangeInfo( String title, boolean majorTitle, String text){
			super();
			
			if (majorTitle){
				this.title = PixelScene.renderText( title, 9 );
				line = new ColorBlock( 1, 1, 0xFF222222);
				add(line);
			} else {
				this.title = PixelScene.renderText( title, 6 );
				line = new ColorBlock( 1, 1, 0xFF333333);
				add(line);
			}
			major = majorTitle;

			add(this.title);

			if (text != null && !text.equals("")){
				this.text = PixelScene.renderMultiline(text, 6);
				add(this.text);
			}

		}

		public void hardlight( int color ){
			title.hardlight( color );
		}

		public void addButton( ChangeButton button ){
			buttons.add(button);
			add(button);

			button.setSize(16, 16);
			layout();
		}

		public boolean onClick( float x, float y ){
			for( ChangeButton button : buttons){
				if (button.inside(x, y)){
					button.onClick();
					return true;
				}
			}
			return false;
		}

		@Override
		protected void layout() {
			float posY = this.y + 2;
			if (major) posY += 2;

			title.x = x + (width - title.width()) / 2f;
			title.y = posY;
			PixelScene.align( title );
			posY += title.baseLine() + 2;

			if (text != null) {
				text.maxWidth((int) width());
				text.setPos(x, posY);
				posY += text.height();
			}

			float posX = x;
			float tallest = 0;
			for (ChangeButton change : buttons){

				if (posX + change.width() >= right()){
					posX = x;
					posY += tallest;
					tallest = 0;
				}

				//centers
				if (posX == x){
					float offset = width;
					for (ChangeButton b : buttons){
						offset -= b.width();
						if (offset <= 0){
							offset += b.width();
							break;
						}
					}
					posX += offset / 2f;
				}

				change.setPos(posX, posY);
				posX += change.width();
				if (tallest < change.height()){
					tallest = change.height();
				}
			}
			posY += tallest + 2;

			height = posY - this.y;
			
			if (major) {
				line.size(width(), 1);
				line.x = x;
				line.y = y+2;
			} else if (x == 0){
				line.size(1, height());
				line.x = width;
				line.y = y;
			} else {
				line.size(1, height());
				line.x = x;
				line.y = y;
			}
		}
	}

	//not actually a button, but functions as one.
	private static class ChangeButton extends Component {

		protected Image icon;
		protected String title;
		protected String message;

		public ChangeButton( Image icon, String title, String message){
			super();

			this.icon = icon;
			add(this.icon);

			this.title = Messages.titleCase(title);
			this.message = message;

			layout();
		}

		public ChangeButton( Item item, String message ){
			this( new ItemSprite(item), item.name(), message);
		}

		protected void onClick() {
			GirlsFrontlinePixelDungeon.scene().add(new ChangesWindow(new Image(icon), title, message));
		}

		@Override
		protected void layout() {
			super.layout();

			icon.x = x + (width - icon.width) / 2f;
			icon.y = y + (height - icon.height) / 2f;
			PixelScene.align(icon);
		}
	}
	
	private static class ChangesWindow extends WndTitledMessage {
	
		public ChangesWindow( Image icon, String title, String message ) {
			super( icon, title, message);
			
			add( new TouchArea( chrome ) {
				@Override
				protected void onClick( Touchscreen.Touch touch ) {
					hide();
				}
			} );
			
		}
		
	}
}
