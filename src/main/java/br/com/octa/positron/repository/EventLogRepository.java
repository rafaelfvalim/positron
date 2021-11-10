package br.com.octa.positron.repository;


import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.octa.positron.model.EventLog;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {

	public List<EventLog> findByLogFirstOccGreaterThanEqual(Date logFirstOccGreater);
}