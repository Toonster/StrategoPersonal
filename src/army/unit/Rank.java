package army.unit;

public enum Rank {
        Bomb(11,0,6), Captain(6,1,4), Colonel(8,1,2),
        Flag(0,0,1), General(9,1,1), Lieutenant(5,1,4),
        Major(7,1,3), Marshal(10,1,1), Miner(4,1,5),
        Scout(2,9,8), Sergeant(4,1,4), Spy(1,1,1);

        private final int strength;
        private final int movementspeed;
        private final int amountOf;

        Rank(int strength, int movementspeed, int amountOf) {
                this.strength = strength;
                this.movementspeed = movementspeed;
                this.amountOf = amountOf;
        }

        public int getMovementspeed() {
                return movementspeed;
        }

        public int getAmountOf() {
                return amountOf;
        }

        public BattleResult getBattleResult(Rank enemyRank) {
                if (this == Rank.Spy && enemyRank == Rank.Marshal) {
                        return BattleResult.WIN;
                }
                if (this == Rank.Miner && enemyRank == Rank.Bomb) {
                        return BattleResult.WIN;
                }
                if (this.strength > enemyRank.strength) {
                        return BattleResult.WIN;
                }

                if (this.strength == enemyRank.strength) {
                        return BattleResult.DRAW;
                }
                return BattleResult.LOSS;
        }
}
