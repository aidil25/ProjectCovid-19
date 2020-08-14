package com.example.projectcovid_19.model;

import com.google.gson.annotations.SerializedName;

public class ResponseProvinsi{

	@SerializedName("provinsi")
	private String provinsi;

	@SerializedName("covid_meninggal")
	private String covidMeninggal;

	@SerializedName("odp_dalam_pemantauan")
	private String odpDalamPemantauan;

	@SerializedName("pdp_masih_dirawat")
	private String pdpMasihDirawat;

	@SerializedName("covid_dirawat")
	private String covidDirawat;

	@SerializedName("covid_isolasi_dirumah")
	private String covidIsolasiDirumah;

	@SerializedName("odp_selesai_pemantauan")
	private String odpSelesaiPemantauan;

	@SerializedName("positif")
	private String positif;

	@SerializedName("kode")
	private String kode;

	@SerializedName("pdp_isolasidirumah")
	private String pdpIsolasidirumah;

	@SerializedName("waktu")
	private String waktu;

	@SerializedName("covid_sembuh")
	private String covidSembuh;

	@SerializedName("pdp_meninggal")
	private String pdpMeninggal;

	@SerializedName("id")
	private String id;

	@SerializedName("total_odp")
	private String totalOdp;

	@SerializedName("pdp")
	private String pdp;

	@SerializedName("pdp_pulangdan_sehat")
	private String pdpPulangdanSehat;

	public String getProvinsi(){
		return provinsi;
	}

	public String getCovidMeninggal(){
		return covidMeninggal;
	}

	public String getOdpDalamPemantauan(){
		return odpDalamPemantauan;
	}

	public String getPdpMasihDirawat(){
		return pdpMasihDirawat;
	}

	public String getCovidDirawat(){
		return covidDirawat;
	}

	public String getCovidIsolasiDirumah(){
		return covidIsolasiDirumah;
	}

	public String getOdpSelesaiPemantauan(){
		return odpSelesaiPemantauan;
	}

	public String getPositif(){
		return positif;
	}

	public String getKode(){
		return kode;
	}

	public String getPdpIsolasidirumah(){
		return pdpIsolasidirumah;
	}

	public String getWaktu(){
		return waktu;
	}

	public String getCovidSembuh(){
		return covidSembuh;
	}

	public String getPdpMeninggal(){
		return pdpMeninggal;
	}

	public String getId(){
		return id;
	}

	public String getTotalOdp(){
		return totalOdp;
	}

	public String getPdp(){
		return pdp;
	}

	public String getPdpPulangdanSehat(){
		return pdpPulangdanSehat;
	}
}