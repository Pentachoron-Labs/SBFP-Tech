package sbfp.client;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import sbfp.tractor.EntityTractor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderTractor extends Render{

	protected final ModelTractor model = new ModelTractor();

	public RenderTractor(){
		this.shadowSize = .5f;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float pitch){
		EntityTractor es = (EntityTractor) entity;
		GL11.glPushMatrix();
		GL11.glTranslated(x,y,z);
		GL11.glRotatef(yaw,0,1,0);
		GL11.glScalef(-1,-1,1);
		this.loadTexture("/mods/sbfp/textures/entity/secret.png");
		this.model.render(entity,0,0,0,0,0,.0625f);
		GL11.glPopMatrix();
	}
}