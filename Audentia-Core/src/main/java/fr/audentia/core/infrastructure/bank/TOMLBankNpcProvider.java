package fr.audentia.core.infrastructure.bank;

import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.core.domain.bank.BankNpcProvider;

import java.io.File;
import java.util.Optional;

public class TOMLBankNpcProvider implements BankNpcProvider {

    private final String filePath;

    public TOMLBankNpcProvider(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<String> getName() {

        FileConfig fileConfig = FileConfig.of(filePath + File.separator + "configuration.toml");

        fileConfig.load();

        Optional<String> optionalNpcName = fileConfig.getOptional("bank.npcName");

        fileConfig.close();

        return optionalNpcName;
    }

}
