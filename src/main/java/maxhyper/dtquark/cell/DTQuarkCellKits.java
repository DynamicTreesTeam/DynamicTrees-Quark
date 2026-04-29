package maxhyper.dtquark.cell;

import com.dtteam.dynamictrees.api.cell.Cell;
import com.dtteam.dynamictrees.api.cell.CellKit;
import com.dtteam.dynamictrees.api.cell.CellNull;
import com.dtteam.dynamictrees.api.cell.CellSolver;
import com.dtteam.dynamictrees.api.registry.Registry;
import com.dtteam.dynamictrees.systems.cell.CellKits;
import com.dtteam.dynamictrees.systems.cell.ConiferBranchCell;
import com.dtteam.dynamictrees.systems.cell.ConiferTopBranchCell;
import com.dtteam.dynamictrees.systems.cell.MetadataCell;
import com.dtteam.dynamictrees.api.voxmap.SimpleVoxmap;
import maxhyper.dtquark.DynamicTreesQuark;

public class DTQuarkCellKits {

    public static final CellKit ANCIENT = new CellKit(DynamicTreesQuark.location("ancient")) {

        private final Cell branchCell = new ConiferBranchCell();
        private final Cell topBranchCell = new ConiferTopBranchCell();

        private final Cell[] leafCells = {
                CellNull.NULL_CELL,
                new AncientLeafCell(1),
                new AncientLeafCell(2),
                new AncientLeafCell(3),
                new AncientLeafCell(4),
                new AncientLeafCell(5),
                new AncientLeafCell(6),
                new AncientLeafCell(7)
        };

        private final CellKits.BasicSolver solver = new com.dtteam.dynamictrees.systems.cell.CellKits.BasicSolver(new short[]{0x0514, 0x0413, 0x0312, 0x0211});

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
            return AncientLeafCell.LEAF_CLUSTER;
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
        registry.register(ANCIENT);
    }

}
