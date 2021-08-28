package lukelin.his.dto.basic.resp.entity;

public class ItemStockSummaryRespDto extends BaseEntityStockSummaryRespDto {
    private ItemRespDto stockEntity;

    public ItemRespDto getStockEntity() {
        return stockEntity;
    }

    public void setStockEntity(ItemRespDto stockEntity) {
        this.stockEntity = stockEntity;
    }
}
