package modelo;

import java.util.Scanner;

public class Cultura {
	private Double Occ; // umidade na capacidade de campo
	private Double Opmp; // umidade no ponto de murcha permanente
	private Double z; // profundidade da raiz
	private Double f; // fator de disponibilidade de agua
	private final Double EF = 0.8; // eficiencia do sistema
	private Double et0; // evapotranspiracao maxima de referencia
	private Double kc; // coeficiente de cultivo maximo;
	private Double ia; // Intensidade de aplicação
	private Double jorn; // Jornada de trabalho
	private Integer nasper; // numero de aspersores
	private Double qAsp;// Vazão do aspersor
	private Double rA; // Raio de alcance
	private Double areaIrrigada; // area irrigada em metros^2 (ha)

	// construtor default
	public Cultura() {
		super();
	}

	public Cultura(Double occ, Double opmp, Double z, Double f, Double et0, Double kc, Double ia, Double jorn,
			Integer nasper, Double qAsp, Double rA, Double areaIrrigada) {
		super();
		this.Occ = occ;
		this.Opmp = opmp;
		this.z = z;
		this.f = f;
		this.et0 = et0;
		this.kc = kc;
		this.ia = ia;
		this.jorn = jorn;
		this.nasper = nasper;
		this.qAsp = qAsp;
		this.rA = rA;
		this.areaIrrigada = areaIrrigada;
	}

	// metodo para calcular a agua disponivel real
	public Double calcularADR() {
		Double adr = (this.getOcc() - this.getOpmp()) * this.getZ() * this.getF();
		return adr;
	}

	// metodo para calcular a irrigacao total necessaria
	public Double calcularITN() {
		Double itn = this.calcularADR() / this.getEF();
		return itn;
	}

	// metodo para calcular a evaporacao potencial de cultura
	public Double calcularETpc() {
		Double etpc = this.getEt0() * this.getKc();
		return etpc;
	}

	// metodo para calcular o turno de rega
	public Double calcularTR() {
		Double tr = this.calcularADR() / this.calcularETpc();
		return Math.floor(tr);
	}

	// metodo para calcular periodo de irrigacao
	public Double calcularPi() {
		Double pi = this.calcularTR() - 1;
		return pi;
	}

	// metodo para calcular o tempo de irrigacao
	public Double calcularTI() {
		Double ia = this.calcularITN() / this.getIa();
		return ia;
	}

	// metodo para calcular o numero de setores irrigados
	public Double calcularNh() {
		Double nh = this.getJorn() / this.calcularTI();
		return Math.floor(nh);
	}

	// metodo para calcular a jornada de trabalho corrigida
	public Double calcularJornCor() {
		Double jorncor = this.calcularNh() * this.calcularTI();
		return jorncor;
	}

	// calcular o numero maximo setor
	public Double calcularNmax() {
		Double nmax = this.calcularNh() * this.calcularPi();
		return nmax;
	}

	// calcular o numero de aspersores por setor
	public Double calcularNaspPS() {
		Double naspps = this.getNasper() / this.calcularNmax();
		return Math.ceil(naspps);
	}

	// calcular a vazao lateral
	public Double calcularQlat() {
		Double qlat = this.getqAsp() * (this.calcularNaspPS() / 2);
		return qlat;
	}

	// calcular a vazao do setor
	public Double calcularQset() {
		Double qset = this.calcularNaspPS() * this.getqAsp();
		return qset;
	}

	// calcular a vazao total com base na cultura
	public Double calcularQCult() {
		Double qCult = (this.getAreaIrrigada() * this.calcularITN() * 10) / (this.calcularPi() * this.calcularNmax());
		return qCult;
	}

	// calcular a margem de erro da vazao total com base na cultura
	public Double calcularErroQCult() {
		Double erroQCult = 100 * (1 - (this.calcularQCult() / this.calcularQset()));
		return erroQCult;
	}

	public Double getOcc() {
		return Occ;
	}

	public void setOcc(Double occ) {
		Occ = occ;
	}

	public Double getOpmp() {
		return Opmp;
	}

	public void setOpmp(Double opmp) {
		Opmp = opmp;
	}

	public Double getZ() {
		return z;
	}

	public void setZ(Double z) {
		this.z = z;
	}

	public Double getF() {
		return f;
	}

	public void setF(Double f) {
		this.f = f;
	}

	public Double getEt0() {
		return et0;
	}

	public void setEt0(Double et0) {
		this.et0 = et0;
	}

	public Double getKc() {
		return kc;
	}

	public void setKc(Double kc) {
		this.kc = kc;
	}

	public Double getEF() {
		return EF;
	}

	public Double getIa() {
		return ia;
	}

	public void setIa(Double ia) {
		this.ia = ia;
	}

	public Double getJorn() {
		return jorn;
	}

	public void setJorn(Double jorn) {
		this.jorn = jorn;
	}

	public Integer getNasper() {
		return nasper;
	}

	public void setNasper(Integer nasper) {
		this.nasper = nasper;
	}

	public Double getqAsp() {
		return qAsp;
	}

	public void setqAsp(Double qAsp) {
		this.qAsp = qAsp;
	}

	public Double getrA() {
		return rA;
	}

	public void setrA(Double rA) {
		this.rA = rA;
	}

	public Double getAreaIrrigada() {
		return areaIrrigada;
	}

	public void setAreaIrrigada(Double areaIrrigada) {
		this.areaIrrigada = areaIrrigada;
	}

	// metodo para imprimir os resultados
	public void imprimir() {
		System.out.println("Calculo da quantidade de agua disponivel: " + this.calcularADR());

		System.out.printf("Calculo da irrigacao total necessaria: %.2f %n", this.calcularITN());

		System.out.printf("Calculo da evapotranspiracao potencial da cultural: %.2f %n",  this.calcularETpc());

		System.out.println("Calculo do turno de rega: " + this.calcularTR());

		System.out.println("Calculo do periodo de irrigacao: " + this.calcularPi());

		System.out.printf("Calculo do tempo de irrigacao: %.2f %n", this.calcularTI());

		System.out.println("Calculo do numero de setores irrigados: " + this.calcularNh());

		System.out.printf("Calculo da jornada de trabalho corrigida: %.1f %n", this.calcularJornCor());

		System.out.println("Calculo do numero maximo setor: " + this.calcularNmax());

		System.out.println("Calculo do numero de aspersores por setor: " + this.calcularNaspPS());

		System.out.println("Calculo da vazao lateral: " + this.calcularQlat());

		System.out.println("Calculo da vazao do setor: " + this.calcularQset());

		System.out.println(
				"Calculo da vazao Total com Base na Cultura:  " + this.calcularQCult() + " metros cubicos por hora");

		System.out.printf(
				"Calculo da margem de erro da vazao total com base na cultura: %.2f %n", this.calcularErroQCult());
	}

	// metodo para inserir os dados
	public void lerDados() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Informe a umidade na capacidade de campo (Occ): ");
		Double Occ1 = sc.nextDouble();
		System.out.println("Informe umidade no ponto de murcha permanente (Opmp):  ");
		Double Opmp1 = sc.nextDouble();
		System.out.println("Informe a profundidade da raiz (Z) em cm: ");
		Double z1 = sc.nextDouble();
		System.out.println("Informe o fator de disponibilidade de agua (f): ");
		Double f1 = sc.nextDouble();
		System.out.println("Informe a evapotranspiracao maxima de referencia (ET0): ");
		Double et01 = sc.nextDouble();
		System.out.println("Informe o coeficiente de cultivo maximo (Kc): ");
		Double kc1 = sc.nextDouble();
		System.out.println("Informe a intensidade de aplicação (Ia): ");
		Double ia1 = sc.nextDouble();
		System.out.println("Informe a jornada de trabalho em horas (Jorn): ");
		Double jorn1 = sc.nextDouble();
		System.out.println("Informe o numero de aspersores: ");
		Integer nasper1 = sc.nextInt();
		System.out.println("Informe a vazao do aspersor: ");
		Double qASp1 = sc.nextDouble();
		System.out.println("Informe o raio de alcance: ");
		Double rA1 = sc.nextDouble();
		System.out.println("Informe a area de irrigacao: ");
		Double aIrr = sc.nextDouble();

		this.setOcc(Occ1);
		this.setOpmp(Opmp1);
		this.setZ(z1);
		this.setF(f1);
		this.setEt0(et01);
		this.setKc(kc1);
		this.setIa(ia1);
		this.setJorn(jorn1);
		this.setNasper(nasper1);
		this.setqAsp(qASp1);
		this.setrA(rA1);
		this.setAreaIrrigada(aIrr);

		sc.close();
	}

}
