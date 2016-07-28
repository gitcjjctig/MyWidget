package com.cj.glsurface;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

public class Sphere extends Mesh{
	float[] vertices;
	short[] indices;
	Vector<Float> vers;
	Vector<Short> ind;
	float mX,mY,mZ,mRadious;
	public Sphere(float x,float y,float z,float radious){
		mX=x;
		mY=y;
		mZ=z;
		mRadious=radious;
		for(int i=0;i<100;i++){
			
		}
	}
	public Sphere(float radious){
		this(0f,0f,0f,radious);
	}
	public void setMaterial(GL10 gl){
		float[] mat_amb = { 0.2f * 0.4f, 0.2f * 0.4f, 0.2f * 1.0f, 1.0f, };
		float[] mat_diff = { 0.4f, 0.4f, 1.0f, 1.0f, };
		float[] mat_spec = { 1.0f, 1.0f, 1.0f, 1.0f, };

		ByteBuffer mabb = ByteBuffer.allocateDirect(mat_amb.length * 4);
		mabb.order(ByteOrder.nativeOrder());
		FloatBuffer mat_ambBuf = mabb.asFloatBuffer();
		mat_ambBuf.put(mat_amb);
		mat_ambBuf.position(0);

		ByteBuffer mdbb = ByteBuffer.allocateDirect(mat_diff.length * 4);
		mdbb.order(ByteOrder.nativeOrder());
		FloatBuffer mat_diffBuf = mdbb.asFloatBuffer();
		mat_diffBuf.put(mat_diff);
		mat_diffBuf.position(0);

		ByteBuffer msbb = ByteBuffer.allocateDirect(mat_spec.length * 4);
		msbb.order(ByteOrder.nativeOrder());
		FloatBuffer mat_specBuf = msbb.asFloatBuffer();
		mat_specBuf.put(mat_spec);
		mat_specBuf.position(0);

		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambBuf);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_diffBuf);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat_specBuf);
		gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 64.0f);

	}
	@Override
	public void draw(GL10 gl) {
		//setMaterial(gl);
		gl.glFrontFace(GL10.GL_CCW);
		// Enable face culling.
		gl.glEnable(GL10.GL_CULL_FACE);
		// What faces to remove with the face culling.
		gl.glCullFace(GL10.GL_BACK);
		float theta, pai;
		float co, si;
		float r1, r2;
		float h1, h2;
		float step = 10.0f;
		float[][] v = new float[32][3];
		ByteBuffer vbb;
		FloatBuffer vBuf;

		vbb = ByteBuffer.allocateDirect(v.length * v[0].length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vBuf = vbb.asFloatBuffer();

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

		for (pai = -90.0f; pai < 90.0f; pai += step) {
			int n = 0;

			r1 = (float) Math.cos(pai * Math.PI / 180.0);
			r2 = (float) Math.cos((pai + step) * Math.PI / 180.0);
			h1 = (float) Math.sin(pai * Math.PI / 180.0);
			h2 = (float) Math.sin((pai + step) * Math.PI / 180.0);

			for (theta = 0.0f; theta <= 360.0f; theta += step) {
				co = (float) Math.cos(theta * Math.PI / 180.0);
				si = -(float) Math.sin(theta * Math.PI / 180.0);

				v[n][0] = (r2 * co)*mRadious;
				v[n][1] = (h2)*mRadious;
				v[n][2] = (r2 * si)*mRadious;
				v[n + 1][0] = (r1 * co)*mRadious;
				v[n + 1][1] = (h1)*mRadious;
				v[n + 1][2] = (r1 * si)*mRadious;

				vBuf.put(v[n]);
				vBuf.put(v[n + 1]);

				n += 2;

				if (n > 31) {
					vBuf.position(0);

					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
					gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
					gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);

					n = 0;
					theta -= step;
				}

			}
			vBuf.position(0);

			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, vBuf);
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
		}

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

	}

}
