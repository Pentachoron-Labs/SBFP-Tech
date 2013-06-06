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
	protected final ModelBoat m2 = new ModelBoat();

	public RenderSecret(){
		this.shadowSize = .5f;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch){
		EntitySecret es = (EntitySecret) entity;
		GL11.glPushMatrix();
		GL11.glTranslated(x,y,z);
		GL11.glRotatef(180-yaw,0,1,0);
		this.loadTexture("/terrain.png");
		float f4 = 0.75F;
		GL11.glScalef(f4,f4,f4);
		GL11.glScalef(1.0F/f4,1.0F/f4,1.0F/f4);
		this.loadTexture("/item/boat.png");
		GL11.glScalef(-1.0F,-1.0F,1.0F);
		this.m2.render(entity,0.0F,0.0F,-0.1F,0.0F,0.0F,0.0625F);
		this.loadTexture("/mods/sbfp/textures/entity/secret.png");
		//		GL11.glScalef(-1.0F,-1.0F,1.0F);
		this.model.render(entity,0,0,0,0,0,.0625f);
		GL11.glPopMatrix();
	}
}