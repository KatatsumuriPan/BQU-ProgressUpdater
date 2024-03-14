package kpan.bqu_prg_updater.asm.hook;

import kpan.bqu_prg_updater.asm.acc.ACC_FontRenderer;
import net.minecraft.client.gui.FontRenderer;

public class HK_FontRenderer {

	public static char onRenderChar(FontRenderer fontRenderer, char ch) {
		if (ch >= '0' && ch <= '9') {
			int color_index = ch - '0';
			//�e�̐F�܂ŏ��������邩�炷�������Â炢
			((ACC_FontRenderer) fontRenderer).setColor(color_index);
		}
		if (ACC_FontRenderer.isFormatSpecial(ch))
			return Character.toUpperCase(ch);
		return ch;
	}
}
