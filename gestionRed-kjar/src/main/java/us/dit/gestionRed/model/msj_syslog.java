package us.dit.gestionRed.model;
/**
 * This class was automatically generated by the data modeler tool.
 */

@javax.persistence.Entity
public class msj_syslog implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	@org.kie.api.definition.type.Label("Marca de tiempo del msj")
	private java.util.Date timestamp;

	private java.lang.String hostname;

	@org.kie.api.definition.type.Label("Servicio concreto con el que se trabaja")
	private java.lang.String process;

	@org.kie.api.definition.type.Label("Identificador del proceso")
	private java.lang.String pid;

	@org.kie.api.definition.type.Label(value = "Mensaje a analizar")
	private java.lang.String msj;

	public msj_syslog() {
	}

	public java.util.Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(java.util.Date timestamp) {
		this.timestamp = timestamp;
	}

	public java.lang.String getHostname() {
		return this.hostname;
	}

	public void setHostname(java.lang.String hostname) {
		this.hostname = hostname;
	}

	public java.lang.String getProcess() {
		return this.process;
	}

	public void setProcess(java.lang.String process) {
		this.process = process;
	}

	public java.lang.String getPid() {
		return this.pid;
	}

	public void setPid(java.lang.String pid) {
		this.pid = pid;
	}

	public java.lang.String getMsj() {
		return this.msj;
	}

	public void setMsj(java.lang.String msj) {
		this.msj = msj;
	}

	public msj_syslog(java.util.Date timestamp, java.lang.String hostname,
			java.lang.String process, java.lang.String pid, java.lang.String msj) {
		this.timestamp = timestamp;
		this.hostname = hostname;
		this.process = process;
		this.pid = pid;
		this.msj = msj;
	}

}