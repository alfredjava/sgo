package sgo.utilidades;

import java.math.BigDecimal;

public class Formula {

	public double calcularVolumenCorregidoDespachado(double volumenObservado,double apiCorregido, double temperaturaObservada){
		

		double K1=0;
		double K0=0;
		double coeficienteDilatacion=0;
		double factorCorrecion=0;
		double volumenCorregido=0;
		
		double densidad =  (double) (141.5 * 999.012/(apiCorregido+131.5));
		//Redondear a dos decimales
		//System.out.println("densidad antes del redondeo");
		//System.out.println(densidad);
		densidad = (double) Math.round(densidad*100000)/100000;
		//System.out.println("densidad despues del redondeo");
		//System.out.println(densidad);
		BigDecimal densidadBig= new BigDecimal(String.valueOf(densidad)).setScale(2, BigDecimal.ROUND_HALF_UP);
		//System.out.println("densidadBig despues del redondeo");
		//System.out.println(densidadBig);
		
		if (apiCorregido < 37.1){
			K1=(double) 0.2701;
		} else if ((apiCorregido>= 37.1) &&(apiCorregido < 48)){
			K1=0;
		} else if ((apiCorregido>= 48) &&(apiCorregido < 52.1)){
			K1= (double) (-0.0018684 * densidad);
		} else if ((apiCorregido>= 52.1) &&(apiCorregido < 85.1)){
			K1=(double) 0.2438;
		}			

		if (apiCorregido < 37.1){
			K0=(double) 103.872;
		} else if ((apiCorregido>= 37.1) &&(apiCorregido < 48)){
			K0=(double) 330.301;
		} else if ((apiCorregido>= 48) &&(apiCorregido < 52.1)){
			K0= (double) (1489.067);
		} else if ((apiCorregido>= 52.1) &&(apiCorregido < 85.1)){
			K0=(double) 192.4571;
		}
		
		coeficienteDilatacion=((K0 / densidad + K1 )/ densidad );	
		//System.out.println("coeficienteDilatacion antes del redondeo");
		//System.out.println(coeficienteDilatacion);
		coeficienteDilatacion = (double)Math.round(coeficienteDilatacion* 10000000)/10000000;
		//System.out.println("coeficienteDilatacion des del redondeo");
		//System.out.println(coeficienteDilatacion);
		factorCorrecion= (double) Math.exp(-coeficienteDilatacion*(temperaturaObservada-60)*(1+0.8*coeficienteDilatacion*(temperaturaObservada-60))) ;
		//System.out.println("factorCorrecion antes del redondeo");
		//System.out.println(factorCorrecion);
		factorCorrecion = (double)Math.round(factorCorrecion* 100000)/100000;
		System.out.println("factorCorrecion");
		System.out.println(factorCorrecion);
		volumenCorregido=factorCorrecion * volumenObservado;
		//System.out.println("XXXXXXXXXXXXvolumenCorregido antes");
		//System.out.println(volumenCorregido);
		volumenCorregido = (double)Math.round(volumenCorregido* 1000)/1000;
		System.out.println("XXXXXXXXXXXXvolumenCorregido");
		System.out.println(volumenCorregido);
		return volumenCorregido;
	}
}
