package raytracer.material;

import raytracer.Color;
import raytracer.Ray;
import raytracer.Tracer;
import raytracer.World;
import raytracer.geometry.Hit;
import raytracer.math.Normal3;
import raytracer.math.Point3;
import raytracer.math.Vector3;

public class TransparentMaterial extends Material{
	private final double indexOfRefraction;
	
	public TransparentMaterial(final double indexOfRefraction){
		this.indexOfRefraction = indexOfRefraction;
	}
	
	@Override
	public Color colorFor(final Hit hit, final World world, final Tracer tracer) {
		// TODO Auto-generated method stub
		
		/*
		 * Formulas:
		 * 		cos phi1 = <-d, n>
		 * 		cos phi2 = sqrt(1 - (eta1 / eta2)² * (1 - cos² phi1))
		 * 		rd = d + 2(cos phi1) * n
		 * 		rd = (eta1 / eta2) * d - (cos phi2 - (eta1 / eta2) * cos phi1) * n
		 * 		c = R * fr[pr, rd] + T * fr[pr, rt], R: = reflection, T := transmission
		 */
		final Normal3 n = hit.normal;
		final Ray ray = hit.ray;
		final Vector3 d = ray.d;
		final Point3 p = ray.at(hit.t);
		final double eta1 = world.indexOfRefraction;
		final double eta2 = indexOfRefraction;
		final double quotient = eta1 / eta2;
		final double cosPhi1 = d.mul(-1).dot(n);
		final double cosPhi2 = Math.sqrt(1 - quotient * quotient * (1 - cosPhi1 * cosPhi1));
		final Vector3 rd = d.add(n.mul(2 * cosPhi1));
		final Vector3 rt = d.mul(quotient).sub(n.mul(cosPhi2 - quotient * cosPhi1));
		final double r0 = Math.pow((eta1 - eta2) / (eta1 + eta2), 2);
		final double r = r0 + (1 - r0) * Math.pow(1 - cosPhi1, 5);
		final double t = 1 - r;
		
		final Color cr = tracer.trace(new Ray(p, rd), world).mul(r);
		final Color ct = tracer.trace(new Ray(p, rt), world).mul(t);
		return cr.add(ct);
	}

}
