package common;

import java.io.Serializable;

/**
 * Ogni sua istanza rappresenta una persona e raccoglie i dati personali inseriti dall'utente.
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 * @author Paradiso Fabiola 749727 VA
 * @author Cuvato Paolo 748691 VA
 */
public class UtenteRegistrato implements Serializable, Comparable<UtenteRegistrato>
{
	
	//campi
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String indirizzoFisico;
	private String email;
	private String password;
	private final String userId;

	/**
	 * Costruisce un oggetto che contiene i dati personali e le credenziali di accesso dell'utente.
	 * @param nome il nome della persona
	 * @param cognome il cognome della persona
	 * @param codiceFiscale il codice fiscale della persona
	 * @param indirizzoFisico l'indirizzo fisico della persona
	 * @param email l'indirizzo e-mail della persona
	 * @param password la password di accesso all'applicazione
	 * @param userId il nome utente di accesso all'applicazione
	 */
	//costruttore
	public UtenteRegistrato(String nome, String cognome, String codiceFiscale, String indirizzoFisico, String email, String password, String userId)
	{
		if(nome.equals("")) throw new NullPointerException("Il campo Nome non può essere vuoto.");
		if(cognome.equals("")) throw new NullPointerException("Il campo Cognome non può essere vuoto.");
		if(codiceFiscale.equals("")) throw new NullPointerException("Il campo Codice Fiscale non può essere vuoto.");
		if(indirizzoFisico.equals("")) throw new NullPointerException("Il campo Indirizzo Fisico non può essere vuoto.");
		if(email.equals("")) throw new NullPointerException("Il campo Email non può essere vuoto.");
		if(password.equals("")) throw new NullPointerException("Il campo Password non può essere vuoto.");
		if(userId.equals("")) throw new NullPointerException("Il campo UserID non può essere vuoto.");

		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.indirizzoFisico = indirizzoFisico;
		this.email = email;
		this.password = password;
		this.userId = userId;
	}

	//getter
	/**
	 * Restituisce il nome della persona rappresentata dall'oggetto.
	 * @return il nome della persona
	 */
	public String getNome()
	{
		return nome;
	}

	/**
	 * Restituisce il cognome della persona rappresentata dall'oggetto.
	 * @return il cognome della persona
	 */
	public String getCognome()
	{
		return cognome;
	}

	/**
	 * Restituisce il codice fiscale della persona rappresentata dall'oggetto.
	 * @return il codice fiscale della persona
	 */
	public String getCodiceFiscale()
	{
		return codiceFiscale;
	}

	/**
	 * Restituisce l'indirizzo fisico della persona rappresentata dall'oggetto.
	 * @return l'indirizzo fisico della persona
	 */
	public String getIndirizzoFisico()
	{
		return indirizzoFisico;
	}

	/**
	 * Restituisce l'indirizzo email della persona rappresentata dall'oggetto.
	 * @return l'indirizzo email della persona
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * Restituisce la password di accesso all'account della persona rappresentata dall'oggetto.
	 * @return la password di accesso della persona
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Restituisce il nome utente della persona rappresentata dall'oggetto.
	 * @return il nome utente della persona
	 */
	public String getUserId(){
		return userId;
	}
	
	//setter
	/**
	 * Assegna il nome fornito come argomento alla persona rappresentata dall'oggetto.
	 * Nel caso in cui ne sia gia' dotata, il nuovo nome viene sovrascritto al precedente.
	 * @param nuovoNome il nome da assegnare alla persona
	 */
	public void setNome(String nuovoNome)
	{
		if(nuovoNome.equals("")) throw new NullPointerException("Il campo Nome non può essere vuoto.");
		this.nome = nuovoNome;
	}

	/**
	 * Assegna il cognome fornito come argomento alla persona rappresentata dall'oggetto.
	 * Nel caso in cui ne sia gia' dotata, il nuovo cognome viene sovrascritto al precedente.
	 * @param nuovoCognome il cognome da assegnare alla persona
	 */
	public void setCognome(String nuovoCognome)
	{
		if(nuovoCognome.equals("")) throw new NullPointerException("Il campo Cognome non può essere vuoto.");
		this.cognome = nuovoCognome;
	}

	/**
	 * Assegna il codice fiscale fornito come argomento alla persona rappresentata dall'oggetto.
	 * Nel caso in cui ne sia gia' dotata, il nuovo codice fiscale viene sovrascritto al precedente.
	 * @param nuovoCodiceFiscale il codice fiscale da assegnare alla persona
	 */
	public void setCodiceFiscale(String nuovoCodiceFiscale)
	{
		if(nuovoCodiceFiscale.equals("")) throw new NullPointerException("Il campo Codice Fiscale non può essere vuoto.");
		this.codiceFiscale = nuovoCodiceFiscale;
	}

	/**
	 * Assegna l'indirizzo fornito come argomento alla persona rappresentata dall'oggetto.
	 * Nel caso in cui ne sia gia' dotata, il nuovo indirizzo viene sovrascritto al precedente.
	 * @param nuovoIndirizzoFisico l'indirizzo da assegnare alla persona
	 */
	public void setIndirizzoFisico(String nuovoIndirizzoFisico)
	{
		if(nuovoIndirizzoFisico.equals("")) throw new NullPointerException("Il campo Indirizzo Fisico non può essere vuoto.");
		this.indirizzoFisico = nuovoIndirizzoFisico;
	}

	/**
	 * Assegna l'indirizzo email fornito come argomento alla persona rappresentata dall'oggetto.
	 * Nel caso in cui ne sia gia' dotata, il nuovo indirizzo email viene sovrascritto al precedente.
	 * @param nuovaEmail l'indirizzo email da assegnare alla persona
	 */
	public void setEmail(String nuovaEmail)
	{
		if(nuovaEmail.equals("")) throw new NullPointerException("Il campo Email non può essere vuoto.");
		this.email = nuovaEmail;
	}

	/**
	 * Assegna la password fornita come argomento alla persona rappresentata dall'oggetto.
	 * Nel caso in cui ne sia gia' dotata, la nuova password viene sovrascritta alla precedente.
	 * @param nuovaPassword la password da assegnare alla persona
	 */
	public void setPassword(String nuovaPassword)
	{
		if(nuovaPassword.equals("")) throw new NullPointerException("Il campo Password non può essere vuoto.");
		this.password = nuovaPassword;
	}

	
	//metodi
	/**
	 * Confronta la persona fornita come argomento con quella che esegue il metodo.
	 * @param utenteRegistrato la persona da confrontare
	 * @return ritorna un intero negativo, zero o un intero positivo se rispettivamente l'oggetto che esegue il metodo e' minore, uguale o maggiore dell'oggetto fornito come argomento
	 * @throws NullPointerException se l'oggetto fornito come argomento e' null
	 * @throws ClassCastException se il tipo dell'oggetto fornito come argomento non gli permette di essere confrontato con quello che esegue il metodo
	 * @see String#compareTo(String)
	 */
	@Override
	public int compareTo(UtenteRegistrato utenteRegistrato)
	{
		if (utenteRegistrato == null) throw new IllegalArgumentException("Comparazione fallita.");
		return codiceFiscale.compareTo(utenteRegistrato.codiceFiscale);
	}
 
}
