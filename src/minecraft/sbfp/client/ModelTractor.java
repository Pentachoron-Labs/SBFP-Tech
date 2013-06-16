package sbfp.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTractor extends ModelBase{

	final ModelRenderer[] mr = new ModelRenderer[16];

	public ModelTractor(){
		textureWidth = 128;
		textureHeight = 128;
		mr[0] = new ModelRenderer(this,0,102);
		mr[1] = new ModelRenderer(this,0,26);
		mr[2] = new ModelRenderer(this,68,48);
		mr[3] = new ModelRenderer(this,55,0);
		mr[4] = new ModelRenderer(this,22,72);
		mr[5] = new ModelRenderer(this,22,72);
		mr[6] = new ModelRenderer(this,0,80);
		mr[7] = new ModelRenderer(this,0,80);
		mr[8] = new ModelRenderer(this,0,46);
		mr[9] = new ModelRenderer(this,0,46);
		mr[10] = new ModelRenderer(this,0,66);
		mr[11] = new ModelRenderer(this,0,70);
		mr[12] = new ModelRenderer(this,0,66);
		mr[13] = new ModelRenderer(this,58,29);
		mr[14] = new ModelRenderer(this,98,0);
		mr[15] = new ModelRenderer(this,0,0);
		mr[0].addBox(-34,-10,-5,12,16,10); //body front
		mr[1].addBox(-22,-10,-6,16,8,12); //body mid
		mr[2].addBox(-11,-2,-6,18,7,12); //body back
		mr[3].addBox(-35,-10.5F,-6,3,4,12); //lights
		mr[4].addBox(-30,3,-6,2,2,12); //front axle
		mr[5].addBox(-1,-1,-8,2,2,16); //back axle
		mr[6].addBox(-33,0,-9,8,8,3); //front right wheel
		mr[7].addBox(-33,0,6,8,8,3); //front left wheel
		mr[8].addBox(-8,-8,-12,16,16,5); //back right wheel
		mr[9].addBox(-8,-8,7,16,16,5); //back left wheel
		mr[10].addBox(-26,-13,3,1,3,1); //smokestack bottom
		mr[11].addBox(-26.5f,-18,2.5F,2,5,2); //smokestack middle
		mr[12].addBox(-26,-21,3,1,3,1); //smokestack top
		mr[13].addBox(-22,-2,-5,10,6,10); //motor
		mr[14].addBox(6,-17,-6,1,15,12); //chair
		mr[15].addBox(7,-2,-2,4,3,4); //hitch
		for(ModelRenderer i:mr){
			i.setRotationPoint(0,0,0);
			i.setTextureSize(textureWidth,textureHeight);
			i.mirror = true;
		}
	}

	@Override
	public void render(Entity entity, float f1, float f2, float f3, float f4, float f5, float f6){
		super.render(entity,f1,f2,f3,f4,f5,f6);
		this.setRotationAngles(f1,f2,f3,f4,f5,f6,entity);
		for(ModelRenderer i:mr){
			i.render(f6);
		}
	}
}