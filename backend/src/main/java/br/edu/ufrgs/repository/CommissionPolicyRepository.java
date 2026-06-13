package br.edu.ufrgs.repository;

import br.edu.ufrgs.dao.csv.CommissionRuleCsvParser;
import br.edu.ufrgs.model.CommissionPolicy;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class CommissionPolicyRepository {

    private CommissionPolicy policy;

    @PostConstruct
    public void init() {
        URL resource = getClass().getClassLoader().getResource("data/regras_comissao.csv");
        if (resource == null) {
            throw new RuntimeException(
                    "Commission rules CSV not found at data/regras_comissao.csv — server cannot start"
            );
        }
        try {
            CommissionRuleCsvParser parser = new CommissionRuleCsvParser();
            this.policy = parser.readRules(resource.getPath());
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load commission rules CSV — server cannot start", e
            );
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(
                    "Invalid data in commission rules CSV — server cannot start", e
            );
        }
    }

    public CommissionPolicy getPolicy() {
        return policy;
    }
}
