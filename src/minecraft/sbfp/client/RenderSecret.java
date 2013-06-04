package sbfp.client;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import sbfp.modsbfp;

public class RenderSecret extends Render{

	protected ModelSecret model = new ModelSecret();

	public RenderSecret(){
		super();
		this.setRenderManager(RenderManager.instance);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch){
		GL11.glPushMatrix();
		GL11.glTranslated(x,y,z);
		GL11.glRotatef(180-yaw,0,1,0);
		this.loadTexture(modsbfp.textureDirectory+"secret.png");
		this.model.render(entity,0,0,0,0,0,.0625f);
		GL11.glPopMatrix();
	}
}