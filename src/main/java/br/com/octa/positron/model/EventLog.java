package br.com.octa.positron.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EventLog")
public class EventLog {
    @Id
	@Column(name = "IdEventLog")
	private Long IdEventLog;
    
	@Column(name = "IdEventClass")
	private int IdEventClass;
	
	@Column(name = "logServerName")
	private String logServerName;
	
	@Column(name = "IdServiceProcess")
	private Long IdServiceProcess;
	
	@Column(name = "logEventOwner")
	private String logEventOwner;
	
	@Column(name = "logUserName")
	private String logUserName;
	
	@Column(name = "logEventSource")
	private String logEventSource;
	
	@Column(name = "logEventType")
	private String logEventType;
	
	@Column(name = "logFirstOcc", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date logFirstOcc;
	
	@Column(name = "logLastOcc", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date logLastOcc;
	
	@Column(name = "logOccCount")
	private int logOccCount;
	
	@Column(name = "logKeyWords")
	private String logKeyWords;
	
	@Column(name = "logDescription")
	private String logDescription;
	
	@Column(name = "logAttachment")
	private String logAttachment;
	
	@Column(name = "logNtfTemp")
	private int logNtfTemp;
	
	@Column(name = "logNtfPerm")
	private int logNtfPerm;
}
