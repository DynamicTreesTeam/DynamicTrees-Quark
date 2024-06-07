package maxhyper.dtquark.cell;

import com.ferreusveritas.dynamictrees.cell.MatrixCell;
import com.ferreusveritas.dynamictrees.util.SimpleVoxmap;
import net.minecraft.core.BlockPos;

public class AshenLeafCell extends MatrixCell {

    public static final SimpleVoxmap LEAF_CLUSTER = new SimpleVoxmap(5, 3, 5, new byte[]{

            // Layer 0 (Bottom)
            0, 0, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 1, 1, 1, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 0, 0,

            // Layer 1 (Middle)
            0, 0, 1, 0, 0,
            0, 1, 2, 1, 0,
            1, 2, 0, 2, 1,
            0, 1, 2, 1, 0,
            0, 0, 1, 0, 0,

            // Layer 2 (Top)
            0, 0, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 1, 1, 1, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 0, 0

    }).setCenter(new BlockPos(2, 1, 2));

    static final byte[] VAL_MAP = {
            0, 1, 2, 0, 2, 0, 0, 0, //D Maps * -> 0
            0, 1, 2, 2, 4, 0, 0, 0, //U Maps 3 -> 2, 4 -> 4, * -> *
            0, 1, 2, 0, 2, 0, 0, 0, //N Maps 3 -> 0, 4 -> 2, * -> *
            0, 1, 2, 0, 2, 0, 0, 0, //S Maps 3 -> 0, 4 -> 2, * -> *
            0, 1, 2, 0, 2, 0, 0, 0, //W Maps 3 -> 0, 4 -> 2, * -> *
            0, 1, 2, 0, 2, 0, 0, 0  //E Maps 3 -> 0, 4 -> 2, * -> *
    };

    public AshenLeafCell(int value) {
        super(value, VAL_MAP);
    }

}
