package br.edu.ufrgs.config;

import br.edu.ufrgs.dao.csv.CommissionRuleCsvParser;
import br.edu.ufrgs.dto.CommissionPolicy;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.io.IOException;
import java.io.InputStream;

@Singleton
@Startup
public class CommissionPolicyLoader {

    private CommissionPolicy commissionPolicy;

    @PostConstruct
    public void load() {
        try {
            CommissionRuleCsvParser parser = new CommissionRuleCsvParser();
            InputStream csv = getClass().getClassLoader()
                    .getResourceAsStream("data/regras_comissao.csv");

            if (csv == null) throw new IllegalStateException("regras_comissao.csv not found in classpath");

            this.commissionPolicy = parser.readRules(csv);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load commission policy", e);
        }
    }

    @Produces
    @ApplicationScoped
    public CommissionPolicy commissionPolicy() {
        return commissionPolicy;
    }
}