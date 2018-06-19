package net.minecraft.server.gui;

import java.util.Vector;
import javax.swing.JList;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ITickable;

public class PlayerListComponent extends JList implements ITickable {

    private final MinecraftServer field_120015_a;
    private int field_120014_b;

    public PlayerListComponent(MinecraftServer minecraftserver) {
        this.field_120015_a = minecraftserver;
        minecraftserver.func_82010_a((ITickable) this);
    }

    public void func_73660_a() {
        if (this.field_120014_b++ % 20 == 0) {
            Vector vector = new Vector();

            for (int i = 0; i < this.field_120015_a.func_184103_al().func_181057_v().size(); ++i) {
                vector.add(((EntityPlayerMP) this.field_120015_a.func_184103_al().func_181057_v().get(i)).func_70005_c_());
            }

            this.setListData(vector);
        }

    }
}
