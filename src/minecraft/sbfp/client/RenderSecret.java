package sbfp.client;

import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import sbfp.secret.EntitySecret;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSecret extends Render{

	protected final ModelSecret model = new ModelSecret();

	public RenderSecret(){
		this.shadowSize = .5f;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch){
		EntitySecret es = (EntitySecret) entity;
		GL11.glPushMatrix();
		GL11.glTranslated(x,y,z);
		GL11.glRotatef(yaw,0,1,0);
		GL11.glScalef(-1,-1,1);
		this.loadTexture("/mods/sbfp/textures/entity/secret.png");
		this.model.render(entity,0,0,0,0,0,.0625f);
		GL11.glPopMatrix();
	}
}