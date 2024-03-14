package kpan.bqu_prg_updater.asm.tf;

import kpan.bqu_prg_updater.asm.core.AsmTypes;
import kpan.bqu_prg_updater.asm.core.AsmUtil;
import kpan.bqu_prg_updater.asm.core.MyAsmNameRemapper.MethodRemap;
import kpan.bqu_prg_updater.asm.core.adapters.Instructions;
import kpan.bqu_prg_updater.asm.core.adapters.MixinAccessorAdapter;
import kpan.bqu_prg_updater.asm.core.adapters.MyClassVisitor;
import kpan.bqu_prg_updater.asm.core.adapters.ReplaceInstructionsAdapter;
import kpan.bqu_prg_updater.asm.core.adapters.ReplaceRefMethodAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class TF_TileEntityFurnace {

	private static final String TARGET = "net.minecraft.tileentity.TileEntityFurnace";
	private static final String HOOK = AsmTypes.HOOK + "HK_" + "TileEntityFurnace";
	private static final String ACC = AsmTypes.ACC + "ACC_" + "TileEntityFurnace";
	private static final MethodRemap readFromNBT = new MethodRemap(TARGET, "readFromNBT", AsmUtil.toMethodDesc(AsmTypes.VOID, AsmTypes.NBTTAGCOMPOUND), "func_145839_a");
	private static final MethodRemap update = new MethodRemap(TARGET, "update", AsmTypes.METHOD_VOID, "func_73660_a");
	private static final MethodRemap getItemBurnTime = new MethodRemap(TARGET, "getItemBurnTime", AsmUtil.toMethodDesc(AsmTypes.INT, AsmTypes.ITEMSTACK), "func_145952_a");
	private static final MethodRemap getName = new MethodRemap(TARGET, "getName", AsmUtil.toMethodDesc(AsmTypes.STRING), "func_70005_c_");

	public static ClassVisitor appendVisitor(ClassVisitor cv, String className) {
		if (!TARGET.equals(className))
			return cv;
		ClassVisitor newcv = new MyClassVisitor(cv, className) {
			@Override
			public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
				MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
				if (readFromNBT.isTarget(name, desc) || update.isTarget(name, desc)) {
					mv = new ReplaceInstructionsAdapter(mv, name,
							Instructions.create()
									.invokeStatic(getItemBurnTime),
							Instructions.create()
									.invokeStatic(HOOK, "getItemBurnTime", AsmUtil.composeRuntimeMethodDesc(AsmTypes.INT, AsmTypes.ITEMSTACK)));
					success();
				}
				return mv;
			}
		}.setSuccessExpected(2);
		newcv = new ReplaceRefMethodAdapter(newcv, HOOK, getName);
		newcv = new MixinAccessorAdapter(newcv, className, ACC);
		return newcv;
	}
}
