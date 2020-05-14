package srpfacadelab;

import java.util.List;

public class ItemController {

    private IGameEngine gameEngine;

    public ItemController(IGameEngine gameEngine)
    {
        this.gameEngine = gameEngine;
    }
    public void useItem(RpgPlayer player, Item item) {
        if (item.getName().equals("Stink Bomb"))
        {
            List<IEnemy> enemies = player.getGameEngine().getEnemiesNear(player);

            for (IEnemy enemy: enemies){
                enemy.takeDamage(100);
            }
        }
    }


    public boolean pickUpItem(RpgPlayer player, Item item) {
        int weight = player.calculateInventoryWeight();
        if (weight + item.getWeight() > player.getCarryingCapacity())
            return false;

        if (item.isUnique() && checkIfItemExistsInInventory(item,player))
            return false;

        // Don't pick up items that give health, just consume them.
        if (item.getHeal() > 0) {
            player.setHealth(player.getHealth() + item.getHeal());

            if (player.getHealth() > player.getMaxHealth())
                player.setHealth(player.getMaxHealth());

            if (item.getHeal() > 500) {
                player.getGameEngine().playSpecialEffect("green_swirly");
            }

            return true;
        }

        if(item.isRare() && item.isUnique()){
            player.getGameEngine().playSpecialEffect("blue_swirly");
        }

        else if (item.isRare())
            player.getGameEngine().playSpecialEffect("cool_swirly_particles");

        player.addToInventory(item);

        calculateStats(player);

        return true;
    }

    private void calculateStats(RpgPlayer player)
    {
        for (Item i: player.getInventory())
        {
            player.setArmour(player.getArmour() + i.getArmour());
        }
    }

    private boolean checkIfItemExistsInInventory(Item item, RpgPlayer player)
    {
        for (Item i: player.getInventory())
        {
            if (i.getId() == item.getId())
                return true;
        }
        return false;
    }


}
