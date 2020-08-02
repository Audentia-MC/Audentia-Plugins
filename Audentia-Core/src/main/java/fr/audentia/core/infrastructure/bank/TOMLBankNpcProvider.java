package fr.audentia.core.infrastructure.bank;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.core.domain.bank.BankNpcProvider;

import java.util.Optional;

public class TOMLBankNpcProvider implements BankNpcProvider {

    private final String filePath;

    public TOMLBankNpcProvider(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<String> getName() {

        FileConfig fileConfig = CommentedFileConfig.builder(filePath)
                .defaultResource("configuration.toml")
                .autosave()
                .build();

        fileConfig.load();

        Optional<String> optionalNpcName = fileConfig.getOptional("bank.npcName");

        fileConfig.close();

        return optionalNpcName;
    }

}
