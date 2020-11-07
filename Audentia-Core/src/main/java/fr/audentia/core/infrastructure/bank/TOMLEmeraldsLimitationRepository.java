package fr.audentia.core.infrastructure.bank;

import com.electronwill.nightconfig.core.file.FileConfig;
import fr.audentia.core.domain.bank.EmeraldsLimitationRepository;
import fr.audentia.core.domain.model.bank.EmeraldsLimitation;
import fr.audentia.players.domain.model.Day;

import java.io.File;

public class TOMLEmeraldsLimitationRepository implements EmeraldsLimitationRepository {

    private final String filePath;

    public TOMLEmeraldsLimitationRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public EmeraldsLimitation getEmeraldsLimitation(Day day) {
        FileConfig fileConfig = FileConfig.of(filePath + File.separator + "configuration.toml");

        fileConfig.load();

        if (fileConfig.isNull("emeralds_limitations." + day.day)) {
            return new EmeraldsLimitation(0);
        }

        int limitation = fileConfig.get("emeralds_limitations." + day.day);

        fileConfig.close();

        return new EmeraldsLimitation(limitation);
    }

}
