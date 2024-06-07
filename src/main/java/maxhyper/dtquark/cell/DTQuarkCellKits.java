package maxhyper.dtquark.cell;

import com.ferreusveritas.dynamictrees.api.cell.Cell;
import com.ferreusveritas.dynamictrees.api.cell.CellKit;
import com.ferreusveritas.dynamictrees.api.cell.CellNull;
import com.ferreusveritas.dynamictrees.api.cell.CellSolver;
import com.ferreusveritas.dynamictrees.api.registry.Registry;
import com.ferreusveritas.dynamictrees.cell.ConiferBranchCell;
import com.ferreusveritas.dynamictrees.cell.ConiferTopBranchCell;
import com.ferreusveritas.dynamictrees.cell.MetadataCell;
import com.ferreusveritas.dynamictrees.util.SimpleVoxmap;
import maxhyper.dtquark.DynamicTreesQuark;

public class DTQuarkCellKits {

    public static final CellKit ANCIENT = new CellKit(DynamicTreesQuark.location("ancient")) {

        private final Cell branchCell = new ConiferBranchCell();
        private final Cell topBranchCell = new ConiferTopBranchCell();

        private final Cell[] leafCells = {
                CellNull.NULL_CELL,
                new AshenLeafCell(1),
                new AshenLeafCell(2),
                new AshenLeafCell(3),
                new AshenLeafCell(4),
                new AshenLeafCell(5),
                new AshenLeafCell(6),
                new AshenLeafCell(7)
        };

        private final com.ferreusveritas.dynamictrees.cell.CellKits.BasicSolver solver = new com.ferreusveritas.dynamictrees.cell.CellKits.BasicSolver(new short[]{0x0514, 0x0413, 0x0312, 0x0211});

        @Override
        public Cell getCellForLeaves(int hydro) {
            return leafCells[hydro];
        }

        @Override
        public Cell getCellForBranch(int radius, int meta) {
            if (meta == MetadataCell.TOP_BRANCH) {
                return topBranchCell;
            } else if (radius == 1) {
                return branchCell;
            } else {
                return CellNull.NULL_CELL;
            }
        }

        @Override
        public SimpleVoxmap getLeafCluster() {
            return AshenLeafCell.LEAF_CLUSTER;
        }

        @Override
        public CellSolver getCellSolver() {
            return solver;
        }

        @Override
        public int getDefaultHydration() {
            return 4;
        }

    };

    public static void register(Registry<CellKit> registry) {
        registry.register(ASHEN);
    }

}
