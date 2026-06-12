package br.edu.ufrgs.dao.export;

import br.edu.ufrgs.model.Seller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ExportFileContract {
    void write(List<Seller> sellers, Path path) throws IOException;
}
