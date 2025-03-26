package elink.suezShimla.water.crm.Login.MMG_MasterData.MMG_Tables.MMG_RealmTable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MMGConsumerDetailSpinnerModel {

    @SerializedName("SR")
    private List<SRModel> SR;

    @SerializedName("DMA")
    private List<DMAModel> DMA;

    public MMGConsumerDetailSpinnerModel() {
    }

    public MMGConsumerDetailSpinnerModel(List<SRModel> SR) {
        this.SR = SR;
    }

    public List<SRModel> getSR() {
        return SR;
    }

    public List<DMAModel> getDMA() {
        return DMA;
    }

    @Override
    public String toString() {
        return "MMGConsumerDetailSpinnerModel{" +
                "SR=" + SR +
                ", DMA=" + DMA +
                '}';
    }
}
