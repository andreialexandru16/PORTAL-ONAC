

package ro.bithat.dms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }
    @Value("${wordpress.url}")
    private String wordpressUrl;
    @Value("${logout.wordpress.url:/}")
    private String logoutWordpressUrl;
    @Value("${provider.host:}")
    private String providerHost;
    @Value("${wso2.active:false}")
    private String wso2Active;
    @Autowired
    private DmswsAuthenticationProvider authProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable().and()

//          .anonymous().disable()
//          .anonymous().principal(buildAnonymousPrinicpal()).and()
                .authorizeRequests()
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/euplatesc/**").permitAll()
                .antMatchers("/version").permitAll()
                .antMatchers("/favicon.ico").permitAll()
//                .antMatchers("/ps4/ecetatean/resetare-parola").permitAll()
                .antMatchers("/test/smartform").permitAll()
//                .antMatchers("/ps4/ecetatean/inregistrare").permitAll()
//                .antMatchers("/ps4/ecetatean/acasa/profil").permitAll()
                .antMatchers("/bugetare-participativa").permitAll()
                .antMatchers("/dmsws/anre/exportXlsx").permitAll()
                .antMatchers("/consultare-cetateni").permitAll()
                .antMatchers("/reabilitare").permitAll()
                .antMatchers("/politica-de-utilizare-cookie-uri").permitAll()
                .antMatchers("/termeni-si-conditii").permitAll()
                .antMatchers("/politica-de-confidentialitate").permitAll()
                .antMatchers("/contact").permitAll()
                .antMatchers("/intrebari-frecvente").permitAll()
                .antMatchers("/servicii-online").permitAll()
                .antMatchers("/lista-servicii").permitAll()
                .antMatchers("/hotarari-consiliu").permitAll()
                .antMatchers("/dispozitii").permitAll()
                .antMatchers("/vizualizare-proiecte").permitAll()
                .antMatchers("/vizualizare-proiecte-consultare").permitAll()
                .antMatchers("/detaliu-proiect").permitAll()
                .antMatchers("/detaliu-serviciu").permitAll()
                //PERMITERE PAGINI PENTRU A AVEA SERVICII PUBLICE
                .antMatchers("/solicitare-noua").permitAll()
                .antMatchers("/solicitare-noua-atasamente").permitAll()
                .antMatchers("/solicitare-revizie-finala").permitAll()
                .antMatchers("/dmsws/lov/**").permitAll()
                .antMatchers("/administrare-calendar").permitAll()
                .antMatchers("/administrare-rezervari").permitAll()
                .antMatchers("/clase-doc**").permitAll()
                .antMatchers("/previzualizare-formular**").permitAll()



                .antMatchers("/csrf").permitAll()
                .antMatchers("/swagger-ui.html").hasRole("ENDPOINT_ADMIN")
                .antMatchers("/swagger*").hasRole("ENDPOINT_ADMIN")
                .antMatchers("/swagger*/**").hasRole("ENDPOINT_ADMIN")
                .antMatchers("/webjars/springfox*/**").hasRole("ENDPOINT_ADMIN")
                .antMatchers("/v2/api-docs").hasRole("ENDPOINT_ADMIN")
                .antMatchers("/actuator/**").hasRole("ENDPOINT_ADMIN")
                .antMatchers("/logs/*").hasRole("ENDPOINT_ADMIN")
                .antMatchers("/logs").hasRole("ENDPOINT_ADMIN")
                .antMatchers("/administrare").hasRole("ENDPOINT_ADMIN")

                .antMatchers("/PORTAL/**").permitAll()
//                .antMatchers("/frontend/ps4/PORTAL/bithat-inregistrare.html").permitAll()
                .antMatchers("/dmsws/nomenclator/**").permitAll()
                .antMatchers("/dmsws/cerericont/**").permitAll()
                .antMatchers("/dmsws/utilizator/addPf").permitAll()
                .antMatchers("/dmsws/utilizator/addPj").permitAll()
                .antMatchers("/dmsws/anre/processExcel").permitAll()
                .antMatchers("/dmsws/anre/processExcelET").permitAll()
                .antMatchers("/dmsws/utilizator/addIp").permitAll()
                .antMatchers("/dmsws/utilizator/addSub").permitAll()
                .antMatchers("/dmsws/utilizator/addChecker").permitAll()
                .antMatchers("/dmsws/utilizator/getWsUrl").permitAll()
                .antMatchers("/dmsws/utilizator/getAnonymousToken").permitAll()
                .antMatchers("/dmsws/utilizator/getPortalUrl").permitAll()
                .antMatchers("/dmsws/utilizator/getWorpresUrl").permitAll()
                .antMatchers("/dmsws/notifAmenzi/getNotifAmenzi").permitAll()
                .antMatchers("/dmsws/notifAmenzi/getAmenzi").permitAll()
                .antMatchers("/dmsws/utilizator/getUnitName").permitAll()
                .antMatchers("/dmsws/utilizator/getLoginUrl").permitAll()
                .antMatchers("/dmsws/utilizator/getLogoutUrl").permitAll()
                .antMatchers("/dmsws/utilizator/getCustomInregistrareUrl").permitAll()
                .antMatchers("/dmsws/utilizator/userIsLogged").permitAll()
                .antMatchers("/dmsws/utilizator/getStatusOrderApi/**").permitAll()
                .antMatchers("/dmsws/utilizator/getStatusOrderApi*").permitAll()
                .antMatchers("/dmsws/utilizator/getWsAndUserInfo").permitAll()
                .antMatchers("/dmsws/utilizator/getIdRegistruDecizii").permitAll()
                .antMatchers("/dmsws/utilizator/getPersoanaFizicaJuridica").permitAll()
                .antMatchers("/dmsws/api/getCaptchaKey").permitAll()
                .antMatchers("/dmsws/utilizator/getFirstName").permitAll()
                .antMatchers("/dmsws/utilizator/getFirstNameAndCompany").permitAll()
                .antMatchers("/dmsws/utilizator/getUsername").permitAll()
                .antMatchers("/dmsws/utilizator/resetPassword").permitAll()
                .antMatchers("/dmsws/utilizator/resetPasswordBody").permitAll()
                .antMatchers("/dmsws/utilizator/resetPassByEmail/**").permitAll()
                .antMatchers("/dmsws/utilizator/getUserInfoByUsername/**").permitAll()
                .antMatchers("/dmsws/utilizator/getInfoPsiholog/**").permitAll()
                .antMatchers("/dmsws/utilizator/updateLoginFailed/**").permitAll()
                .antMatchers("/dmsws/utilizator/addPsihologExtension").permitAll()
                .antMatchers("/dmsws/users/getSubconturi").permitAll()
                .antMatchers("/dmsws/users/insertSubcont/**").permitAll()
                .antMatchers("/dmsws/meniu/all_menu_web_electronic**").permitAll()
                .antMatchers("/dmsws/document/getListaClasaDocByTag**").permitAll()

                .antMatchers("/PORTAL/bithat-resetare-parola.html").permitAll()
                .antMatchers("/PORTAL/bithat-schimbare-parola.html").permitAll()

                .antMatchers("/dmsws/utilizator/resetPassword").permitAll()
                .antMatchers("/dmsws/project/extern_projects/*").permitAll()

                .antMatchers("/dmsws/portalflow/**").permitAll()
                .antMatchers("/dmsws/project/**").permitAll()
                .antMatchers("/dmsws/bt").permitAll()
                .antMatchers("/dmsws/download**").permitAll()
                .antMatchers("/dmsws/regsistratura/getInregistrariListCount/*").permitAll()
                .antMatchers("/dmsws/anre_licente/getLicenteFilteredCount/*").permitAll()
                .antMatchers("/dmsws/anre_licente/getLicenteFiltered/*").permitAll()
                .antMatchers("/dmsws/regsistratura/getCriteriiCautare/*").permitAll()
                .antMatchers("/dmsws/folder/getSubfoldersAndFiles/*").permitAll()
                .antMatchers("/dmsws/document/electronic_services").permitAll()
                .antMatchers("/dmsws/anre").permitAll()
                .antMatchers("/getSubfoldersAndFiles/*").permitAll()
                .antMatchers("/dmsws/regsistratura/getInregistrariList/*").permitAll()
                .antMatchers("/dmsws/regsistratura/getInregistrariListCautareCountP/*").permitAll()
                .antMatchers("/dmsws/regsistratura/getInregistrariListCautareP/*").permitAll()
                .antMatchers("/dmsws/regsistratura/getInregistrariListFiltrare/*").permitAll()
                .antMatchers("/dmsws/anre/getExameneInstalatori").permitAll()
                .antMatchers("/dmsws/anre/getAutorizatiiInstalatori").permitAll()
                .antMatchers("/dmsws/anre/getAtestate/*").permitAll()
                .antMatchers("/dmsws/anre/getAutorizatiiInstalatoriCount/*").permitAll()
                .antMatchers("/dmsws/anre/getExameneInstalatoriCount/*").permitAll()
                .antMatchers("/dmsws/anre/getAtestateCount/*").permitAll()
                .antMatchers("/dmsws/anre/getAutorizatiiElectricieniCount/*").permitAll()
                .antMatchers("/dmsws/anre/getAutorizatiiElectricieni").permitAll()
                .antMatchers("/dmsws/anre/getExameneElectricieniCount/*").permitAll()
                .antMatchers("/dmsws/anre/getExameneElectricieni").permitAll()
                .antMatchers("/dmsws/bt/getStatusOrderApi/**").permitAll()
                .antMatchers("/dmsws/anre/exportXlsx**").permitAll()

                //.antMatchers("/dmsws/project/getPerioadaDepunere").permitAll()


                .antMatchers("/actuator/info").permitAll()
                .antMatchers("/VAADIN/**").permitAll()
                .antMatchers("/DMSWS_PROXY/**").permitAll()
                .antMatchers("/zuul/DMSWS_PROXY/**").permitAll()
                .antMatchers("/frontend/**").permitAll()
                .antMatchers("/website/**").permitAll()
                //  .antMatchers("/project/**").permitAll()
                //.antMatchers("/v2/api-docs").permitAll()
//          .antMatchers("/VAADIN/static/*").permitAll()
//          .antMatchers("/admin/**").hasRole("ADMIN")
//          .antMatchers("/anonymous*").anonymous()
                .antMatchers("/login*").permitAll()
                .antMatchers("/PISC/login2/**").permitAll()
                .antMatchers("/login2/**").permitAll()
                .antMatchers("/health").permitAll()
                //.antMatchers("/dmsws/ps4/**").permitAll()
                .anyRequest().authenticated();

                // daca e wso2 activ
                if (wso2Active != null && wso2Active.trim().toLowerCase().equals("true")){
                    http.oauth2Login()
                            .loginPage("/login");
                } else {
                    http.formLogin()
                            .loginPage("/PORTAL/autentificare.html")
                            .loginProcessingUrl("/login");
                }

                //.defaultSuccessUrl("https://eadministratie.cjbotosani.ro/", false)
                //.failureUrl("/login.html?error=true")
//          .failureHandler(authenticationFailureHandler())
        http
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler());
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        http.sessionManagement()
                .maximumSessions(1).and().invalidSessionUrl("/expired");

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String getWordpressUrl() {
        return wordpressUrl;
    }

    public void setWordpressUrl(String wordpressUrl) {
        this.wordpressUrl = wordpressUrl;
    }

    public String getLogoutWordpressUrl() {
        return logoutWordpressUrl;
    }

    public void setLogoutWordpressUrl(String logoutWordpressUrl) {
        this.logoutWordpressUrl = logoutWordpressUrl;
    }

    public String getFinalLogoutWordpressUrl() {
        return getLogoutWordpressUrl().equals("/")?getWordpressUrl():getLogoutWordpressUrl();
    }

}