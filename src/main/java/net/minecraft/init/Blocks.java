package net.minecraft.init;

import com.google.common.collect.Sets;
import java.util.Set;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.BlockPistonMoving;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.util.ResourceLocation;

public class Blocks {

    private static final Set<Block> field_185780_dg;
    public static final Block field_150350_a;
    public static final Block field_150348_b;
    public static final BlockGrass field_150349_c;
    public static final Block field_150346_d;
    public static final Block field_150347_e;
    public static final Block field_150344_f;
    public static final Block field_150345_g;
    public static final Block field_150357_h;
    public static final BlockDynamicLiquid field_150358_i;
    public static final BlockStaticLiquid field_150355_j;
    public static final BlockDynamicLiquid field_150356_k;
    public static final BlockStaticLiquid field_150353_l;
    public static final BlockSand field_150354_m;
    public static final Block field_150351_n;
    public static final Block field_150352_o;
    public static final Block field_150366_p;
    public static final Block field_150365_q;
    public static final Block field_150364_r;
    public static final Block field_150363_s;
    public static final BlockLeaves field_150362_t;
    public static final BlockLeaves field_150361_u;
    public static final Block field_150360_v;
    public static final Block field_150359_w;
    public static final Block field_150369_x;
    public static final Block field_150368_y;
    public static final Block field_150367_z;
    public static final Block field_150322_A;
    public static final Block field_150323_B;
    public static final Block field_150324_C;
    public static final Block field_150318_D;
    public static final Block field_150319_E;
    public static final BlockPistonBase field_150320_F;
    public static final Block field_150321_G;
    public static final BlockTallGrass field_150329_H;
    public static final BlockDeadBush field_150330_I;
    public static final BlockPistonBase field_150331_J;
    public static final BlockPistonExtension field_150332_K;
    public static final Block field_150325_L;
    public static final BlockPistonMoving field_180384_M;
    public static final BlockFlower field_150327_N;
    public static final BlockFlower field_150328_O;
    public static final BlockBush field_150338_P;
    public static final BlockBush field_150337_Q;
    public static final Block field_150340_R;
    public static final Block field_150339_S;
    public static final BlockSlab field_150334_T;
    public static final BlockSlab field_150333_U;
    public static final Block field_150336_V;
    public static final Block field_150335_W;
    public static final Block field_150342_X;
    public static final Block field_150341_Y;
    public static final Block field_150343_Z;
    public static final Block field_150478_aa;
    public static final BlockFire field_150480_ab;
    public static final Block field_150474_ac;
    public static final Block field_150476_ad;
    public static final BlockChest field_150486_ae;
    public static final BlockRedstoneWire field_150488_af;
    public static final Block field_150482_ag;
    public static final Block field_150484_ah;
    public static final Block field_150462_ai;
    public static final Block field_150464_aj;
    public static final Block field_150458_ak;
    public static final Block field_150460_al;
    public static final Block field_150470_am;
    public static final Block field_150472_an;
    public static final BlockDoor field_180413_ao;
    public static final BlockDoor field_180414_ap;
    public static final BlockDoor field_180412_aq;
    public static final BlockDoor field_180411_ar;
    public static final BlockDoor field_180410_as;
    public static final BlockDoor field_180409_at;
    public static final Block field_150468_ap;
    public static final Block field_150448_aq;
    public static final Block field_150446_ar;
    public static final Block field_150444_as;
    public static final Block field_150442_at;
    public static final Block field_150456_au;
    public static final BlockDoor field_150454_av;
    public static final Block field_150452_aw;
    public static final Block field_150450_ax;
    public static final Block field_150439_ay;
    public static final Block field_150437_az;
    public static final Block field_150429_aA;
    public static final Block field_150430_aB;
    public static final Block field_150431_aC;
    public static final Block field_150432_aD;
    public static final Block field_150433_aE;
    public static final BlockCactus field_150434_aF;
    public static final Block field_150435_aG;
    public static final BlockReed field_150436_aH;
    public static final Block field_150421_aI;
    public static final Block field_180407_aO;
    public static final Block field_180408_aP;
    public static final Block field_180404_aQ;
    public static final Block field_180403_aR;
    public static final Block field_180406_aS;
    public static final Block field_180405_aT;
    public static final Block field_150423_aK;
    public static final Block field_150424_aL;
    public static final Block field_150425_aM;
    public static final Block field_150426_aN;
    public static final BlockPortal field_150427_aO;
    public static final Block field_150428_aP;
    public static final Block field_150414_aQ;
    public static final BlockRedstoneRepeater field_150413_aR;
    public static final BlockRedstoneRepeater field_150416_aS;
    public static final Block field_150415_aT;
    public static final Block field_150418_aU;
    public static final Block field_150417_aV;
    public static final Block field_150420_aW;
    public static final Block field_150419_aX;
    public static final Block field_150411_aY;
    public static final Block field_150410_aZ;
    public static final Block field_150440_ba;
    public static final Block field_150393_bb;
    public static final Block field_150394_bc;
    public static final Block field_150395_bd;
    public static final Block field_180390_bo;
    public static final Block field_180391_bp;
    public static final Block field_180392_bq;
    public static final Block field_180386_br;
    public static final Block field_180385_bs;
    public static final Block field_180387_bt;
    public static final Block field_150389_bf;
    public static final Block field_150390_bg;
    public static final BlockMycelium field_150391_bh;
    public static final Block field_150392_bi;
    public static final Block field_150385_bj;
    public static final Block field_150386_bk;
    public static final Block field_150387_bl;
    public static final Block field_150388_bm;
    public static final Block field_150381_bn;
    public static final Block field_150382_bo;
    public static final BlockCauldron field_150383_bp;
    public static final Block field_150384_bq;
    public static final Block field_150378_br;
    public static final Block field_150377_bs;
    public static final Block field_150380_bt;
    public static final Block field_150379_bu;
    public static final Block field_150374_bv;
    public static final BlockSlab field_150373_bw;
    public static final BlockSlab field_150376_bx;
    public static final Block field_150375_by;
    public static final Block field_150372_bz;
    public static final Block field_150412_bA;
    public static final Block field_150477_bB;
    public static final BlockTripWireHook field_150479_bC;
    public static final Block field_150473_bD;
    public static final Block field_150475_bE;
    public static final Block field_150485_bF;
    public static final Block field_150487_bG;
    public static final Block field_150481_bH;
    public static final Block field_150483_bI;
    public static final BlockBeacon field_150461_bJ;
    public static final Block field_150463_bK;
    public static final Block field_150457_bL;
    public static final Block field_150459_bM;
    public static final Block field_150469_bN;
    public static final Block field_150471_bO;
    public static final BlockSkull field_150465_bP;
    public static final Block field_150467_bQ;
    public static final Block field_150447_bR;
    public static final Block field_150445_bS;
    public static final Block field_150443_bT;
    public static final BlockRedstoneComparator field_150441_bU;
    public static final BlockRedstoneComparator field_150455_bV;
    public static final BlockDaylightDetector field_150453_bW;
    public static final BlockDaylightDetector field_180402_cm;
    public static final Block field_150451_bX;
    public static final Block field_150449_bY;
    public static final BlockHopper field_150438_bZ;
    public static final Block field_150371_ca;
    public static final Block field_150370_cb;
    public static final Block field_150408_cc;
    public static final Block field_150409_cd;
    public static final Block field_150406_ce;
    public static final Block field_180401_cv;
    public static final Block field_180400_cw;
    public static final Block field_150407_cf;
    public static final Block field_150404_cg;
    public static final Block field_150405_ch;
    public static final Block field_150402_ci;
    public static final Block field_150403_cj;
    public static final Block field_150400_ck;
    public static final Block field_150401_cl;
    public static final Block field_180399_cE;
    public static final BlockDoublePlant field_150398_cm;
    public static final BlockStainedGlass field_150399_cn;
    public static final BlockStainedGlassPane field_150397_co;
    public static final Block field_180397_cI;
    public static final Block field_180398_cJ;
    public static final Block field_180393_cK;
    public static final Block field_180394_cL;
    public static final Block field_180395_cM;
    public static final Block field_180396_cN;
    public static final BlockSlab field_180388_cO;
    public static final BlockSlab field_180389_cP;
    public static final Block field_185764_cQ;
    public static final Block field_185765_cR;
    public static final Block field_185766_cS;
    public static final Block field_185767_cT;
    public static final Block field_185768_cU;
    public static final Block field_185769_cV;
    public static final BlockSlab field_185770_cW;
    public static final BlockSlab field_185771_cX;
    public static final Block field_185772_cY;
    public static final Block field_185773_cZ;
    public static final Block field_185774_da;
    public static final Block field_185775_db;
    public static final Block field_185776_dc;
    public static final Block field_185777_dd;
    public static final Block field_185778_de;
    public static final Block field_189877_df;
    public static final Block field_189878_dg;
    public static final Block field_189879_dh;
    public static final Block field_189880_di;
    public static final Block field_189881_dj;
    public static final Block field_190976_dk;
    public static final Block field_190977_dl;
    public static final Block field_190978_dm;
    public static final Block field_190979_dn;
    public static final Block field_190980_do;
    public static final Block field_190981_dp;
    public static final Block field_190982_dq;
    public static final Block field_190983_dr;
    public static final Block field_190984_ds;
    public static final Block field_190985_dt;
    public static final Block field_190986_du;
    public static final Block field_190987_dv;
    public static final Block field_190988_dw;
    public static final Block field_190989_dx;
    public static final Block field_190990_dy;
    public static final Block field_190991_dz;
    public static final Block field_190975_dA;
    public static final Block field_192427_dB;
    public static final Block field_192428_dC;
    public static final Block field_192429_dD;
    public static final Block field_192430_dE;
    public static final Block field_192431_dF;
    public static final Block field_192432_dG;
    public static final Block field_192433_dH;
    public static final Block field_192434_dI;
    public static final Block field_192435_dJ;
    public static final Block field_192436_dK;
    public static final Block field_192437_dL;
    public static final Block field_192438_dM;
    public static final Block field_192439_dN;
    public static final Block field_192440_dO;
    public static final Block field_192441_dP;
    public static final Block field_192442_dQ;
    public static final Block field_192443_dR;
    public static final Block field_192444_dS;
    public static final Block field_185779_df;

    @Nullable
    private static Block func_180383_a(String s) {
        Block block = (Block) Block.field_149771_c.func_82594_a(new ResourceLocation(s));

        if (!Blocks.field_185780_dg.add(block)) {
            throw new IllegalStateException("Invalid Block requested: " + s);
        } else {
            return block;
        }
    }

    static {
        if (!Bootstrap.func_179869_a()) {
            throw new RuntimeException("Accessed Blocks before Bootstrap!");
        } else {
            field_185780_dg = Sets.newHashSet();
            field_150350_a = func_180383_a("air");
            field_150348_b = func_180383_a("stone");
            field_150349_c = (BlockGrass) func_180383_a("grass");
            field_150346_d = func_180383_a("dirt");
            field_150347_e = func_180383_a("cobblestone");
            field_150344_f = func_180383_a("planks");
            field_150345_g = func_180383_a("sapling");
            field_150357_h = func_180383_a("bedrock");
            field_150358_i = (BlockDynamicLiquid) func_180383_a("flowing_water");
            field_150355_j = (BlockStaticLiquid) func_180383_a("water");
            field_150356_k = (BlockDynamicLiquid) func_180383_a("flowing_lava");
            field_150353_l = (BlockStaticLiquid) func_180383_a("lava");
            field_150354_m = (BlockSand) func_180383_a("sand");
            field_150351_n = func_180383_a("gravel");
            field_150352_o = func_180383_a("gold_ore");
            field_150366_p = func_180383_a("iron_ore");
            field_150365_q = func_180383_a("coal_ore");
            field_150364_r = func_180383_a("log");
            field_150363_s = func_180383_a("log2");
            field_150362_t = (BlockLeaves) func_180383_a("leaves");
            field_150361_u = (BlockLeaves) func_180383_a("leaves2");
            field_150360_v = func_180383_a("sponge");
            field_150359_w = func_180383_a("glass");
            field_150369_x = func_180383_a("lapis_ore");
            field_150368_y = func_180383_a("lapis_block");
            field_150367_z = func_180383_a("dispenser");
            field_150322_A = func_180383_a("sandstone");
            field_150323_B = func_180383_a("noteblock");
            field_150324_C = func_180383_a("bed");
            field_150318_D = func_180383_a("golden_rail");
            field_150319_E = func_180383_a("detector_rail");
            field_150320_F = (BlockPistonBase) func_180383_a("sticky_piston");
            field_150321_G = func_180383_a("web");
            field_150329_H = (BlockTallGrass) func_180383_a("tallgrass");
            field_150330_I = (BlockDeadBush) func_180383_a("deadbush");
            field_150331_J = (BlockPistonBase) func_180383_a("piston");
            field_150332_K = (BlockPistonExtension) func_180383_a("piston_head");
            field_150325_L = func_180383_a("wool");
            field_180384_M = (BlockPistonMoving) func_180383_a("piston_extension");
            field_150327_N = (BlockFlower) func_180383_a("yellow_flower");
            field_150328_O = (BlockFlower) func_180383_a("red_flower");
            field_150338_P = (BlockBush) func_180383_a("brown_mushroom");
            field_150337_Q = (BlockBush) func_180383_a("red_mushroom");
            field_150340_R = func_180383_a("gold_block");
            field_150339_S = func_180383_a("iron_block");
            field_150334_T = (BlockSlab) func_180383_a("double_stone_slab");
            field_150333_U = (BlockSlab) func_180383_a("stone_slab");
            field_150336_V = func_180383_a("brick_block");
            field_150335_W = func_180383_a("tnt");
            field_150342_X = func_180383_a("bookshelf");
            field_150341_Y = func_180383_a("mossy_cobblestone");
            field_150343_Z = func_180383_a("obsidian");
            field_150478_aa = func_180383_a("torch");
            field_150480_ab = (BlockFire) func_180383_a("fire");
            field_150474_ac = func_180383_a("mob_spawner");
            field_150476_ad = func_180383_a("oak_stairs");
            field_150486_ae = (BlockChest) func_180383_a("chest");
            field_150488_af = (BlockRedstoneWire) func_180383_a("redstone_wire");
            field_150482_ag = func_180383_a("diamond_ore");
            field_150484_ah = func_180383_a("diamond_block");
            field_150462_ai = func_180383_a("crafting_table");
            field_150464_aj = func_180383_a("wheat");
            field_150458_ak = func_180383_a("farmland");
            field_150460_al = func_180383_a("furnace");
            field_150470_am = func_180383_a("lit_furnace");
            field_150472_an = func_180383_a("standing_sign");
            field_180413_ao = (BlockDoor) func_180383_a("wooden_door");
            field_180414_ap = (BlockDoor) func_180383_a("spruce_door");
            field_180412_aq = (BlockDoor) func_180383_a("birch_door");
            field_180411_ar = (BlockDoor) func_180383_a("jungle_door");
            field_180410_as = (BlockDoor) func_180383_a("acacia_door");
            field_180409_at = (BlockDoor) func_180383_a("dark_oak_door");
            field_150468_ap = func_180383_a("ladder");
            field_150448_aq = func_180383_a("rail");
            field_150446_ar = func_180383_a("stone_stairs");
            field_150444_as = func_180383_a("wall_sign");
            field_150442_at = func_180383_a("lever");
            field_150456_au = func_180383_a("stone_pressure_plate");
            field_150454_av = (BlockDoor) func_180383_a("iron_door");
            field_150452_aw = func_180383_a("wooden_pressure_plate");
            field_150450_ax = func_180383_a("redstone_ore");
            field_150439_ay = func_180383_a("lit_redstone_ore");
            field_150437_az = func_180383_a("unlit_redstone_torch");
            field_150429_aA = func_180383_a("redstone_torch");
            field_150430_aB = func_180383_a("stone_button");
            field_150431_aC = func_180383_a("snow_layer");
            field_150432_aD = func_180383_a("ice");
            field_150433_aE = func_180383_a("snow");
            field_150434_aF = (BlockCactus) func_180383_a("cactus");
            field_150435_aG = func_180383_a("clay");
            field_150436_aH = (BlockReed) func_180383_a("reeds");
            field_150421_aI = func_180383_a("jukebox");
            field_180407_aO = func_180383_a("fence");
            field_180408_aP = func_180383_a("spruce_fence");
            field_180404_aQ = func_180383_a("birch_fence");
            field_180403_aR = func_180383_a("jungle_fence");
            field_180406_aS = func_180383_a("dark_oak_fence");
            field_180405_aT = func_180383_a("acacia_fence");
            field_150423_aK = func_180383_a("pumpkin");
            field_150424_aL = func_180383_a("netherrack");
            field_150425_aM = func_180383_a("soul_sand");
            field_150426_aN = func_180383_a("glowstone");
            field_150427_aO = (BlockPortal) func_180383_a("portal");
            field_150428_aP = func_180383_a("lit_pumpkin");
            field_150414_aQ = func_180383_a("cake");
            field_150413_aR = (BlockRedstoneRepeater) func_180383_a("unpowered_repeater");
            field_150416_aS = (BlockRedstoneRepeater) func_180383_a("powered_repeater");
            field_150415_aT = func_180383_a("trapdoor");
            field_150418_aU = func_180383_a("monster_egg");
            field_150417_aV = func_180383_a("stonebrick");
            field_150420_aW = func_180383_a("brown_mushroom_block");
            field_150419_aX = func_180383_a("red_mushroom_block");
            field_150411_aY = func_180383_a("iron_bars");
            field_150410_aZ = func_180383_a("glass_pane");
            field_150440_ba = func_180383_a("melon_block");
            field_150393_bb = func_180383_a("pumpkin_stem");
            field_150394_bc = func_180383_a("melon_stem");
            field_150395_bd = func_180383_a("vine");
            field_180390_bo = func_180383_a("fence_gate");
            field_180391_bp = func_180383_a("spruce_fence_gate");
            field_180392_bq = func_180383_a("birch_fence_gate");
            field_180386_br = func_180383_a("jungle_fence_gate");
            field_180385_bs = func_180383_a("dark_oak_fence_gate");
            field_180387_bt = func_180383_a("acacia_fence_gate");
            field_150389_bf = func_180383_a("brick_stairs");
            field_150390_bg = func_180383_a("stone_brick_stairs");
            field_150391_bh = (BlockMycelium) func_180383_a("mycelium");
            field_150392_bi = func_180383_a("waterlily");
            field_150385_bj = func_180383_a("nether_brick");
            field_150386_bk = func_180383_a("nether_brick_fence");
            field_150387_bl = func_180383_a("nether_brick_stairs");
            field_150388_bm = func_180383_a("nether_wart");
            field_150381_bn = func_180383_a("enchanting_table");
            field_150382_bo = func_180383_a("brewing_stand");
            field_150383_bp = (BlockCauldron) func_180383_a("cauldron");
            field_150384_bq = func_180383_a("end_portal");
            field_150378_br = func_180383_a("end_portal_frame");
            field_150377_bs = func_180383_a("end_stone");
            field_150380_bt = func_180383_a("dragon_egg");
            field_150379_bu = func_180383_a("redstone_lamp");
            field_150374_bv = func_180383_a("lit_redstone_lamp");
            field_150373_bw = (BlockSlab) func_180383_a("double_wooden_slab");
            field_150376_bx = (BlockSlab) func_180383_a("wooden_slab");
            field_150375_by = func_180383_a("cocoa");
            field_150372_bz = func_180383_a("sandstone_stairs");
            field_150412_bA = func_180383_a("emerald_ore");
            field_150477_bB = func_180383_a("ender_chest");
            field_150479_bC = (BlockTripWireHook) func_180383_a("tripwire_hook");
            field_150473_bD = func_180383_a("tripwire");
            field_150475_bE = func_180383_a("emerald_block");
            field_150485_bF = func_180383_a("spruce_stairs");
            field_150487_bG = func_180383_a("birch_stairs");
            field_150481_bH = func_180383_a("jungle_stairs");
            field_150483_bI = func_180383_a("command_block");
            field_150461_bJ = (BlockBeacon) func_180383_a("beacon");
            field_150463_bK = func_180383_a("cobblestone_wall");
            field_150457_bL = func_180383_a("flower_pot");
            field_150459_bM = func_180383_a("carrots");
            field_150469_bN = func_180383_a("potatoes");
            field_150471_bO = func_180383_a("wooden_button");
            field_150465_bP = (BlockSkull) func_180383_a("skull");
            field_150467_bQ = func_180383_a("anvil");
            field_150447_bR = func_180383_a("trapped_chest");
            field_150445_bS = func_180383_a("light_weighted_pressure_plate");
            field_150443_bT = func_180383_a("heavy_weighted_pressure_plate");
            field_150441_bU = (BlockRedstoneComparator) func_180383_a("unpowered_comparator");
            field_150455_bV = (BlockRedstoneComparator) func_180383_a("powered_comparator");
            field_150453_bW = (BlockDaylightDetector) func_180383_a("daylight_detector");
            field_180402_cm = (BlockDaylightDetector) func_180383_a("daylight_detector_inverted");
            field_150451_bX = func_180383_a("redstone_block");
            field_150449_bY = func_180383_a("quartz_ore");
            field_150438_bZ = (BlockHopper) func_180383_a("hopper");
            field_150371_ca = func_180383_a("quartz_block");
            field_150370_cb = func_180383_a("quartz_stairs");
            field_150408_cc = func_180383_a("activator_rail");
            field_150409_cd = func_180383_a("dropper");
            field_150406_ce = func_180383_a("stained_hardened_clay");
            field_180401_cv = func_180383_a("barrier");
            field_180400_cw = func_180383_a("iron_trapdoor");
            field_150407_cf = func_180383_a("hay_block");
            field_150404_cg = func_180383_a("carpet");
            field_150405_ch = func_180383_a("hardened_clay");
            field_150402_ci = func_180383_a("coal_block");
            field_150403_cj = func_180383_a("packed_ice");
            field_150400_ck = func_180383_a("acacia_stairs");
            field_150401_cl = func_180383_a("dark_oak_stairs");
            field_180399_cE = func_180383_a("slime");
            field_150398_cm = (BlockDoublePlant) func_180383_a("double_plant");
            field_150399_cn = (BlockStainedGlass) func_180383_a("stained_glass");
            field_150397_co = (BlockStainedGlassPane) func_180383_a("stained_glass_pane");
            field_180397_cI = func_180383_a("prismarine");
            field_180398_cJ = func_180383_a("sea_lantern");
            field_180393_cK = func_180383_a("standing_banner");
            field_180394_cL = func_180383_a("wall_banner");
            field_180395_cM = func_180383_a("red_sandstone");
            field_180396_cN = func_180383_a("red_sandstone_stairs");
            field_180388_cO = (BlockSlab) func_180383_a("double_stone_slab2");
            field_180389_cP = (BlockSlab) func_180383_a("stone_slab2");
            field_185764_cQ = func_180383_a("end_rod");
            field_185765_cR = func_180383_a("chorus_plant");
            field_185766_cS = func_180383_a("chorus_flower");
            field_185767_cT = func_180383_a("purpur_block");
            field_185768_cU = func_180383_a("purpur_pillar");
            field_185769_cV = func_180383_a("purpur_stairs");
            field_185770_cW = (BlockSlab) func_180383_a("purpur_double_slab");
            field_185771_cX = (BlockSlab) func_180383_a("purpur_slab");
            field_185772_cY = func_180383_a("end_bricks");
            field_185773_cZ = func_180383_a("beetroots");
            field_185774_da = func_180383_a("grass_path");
            field_185775_db = func_180383_a("end_gateway");
            field_185776_dc = func_180383_a("repeating_command_block");
            field_185777_dd = func_180383_a("chain_command_block");
            field_185778_de = func_180383_a("frosted_ice");
            field_189877_df = func_180383_a("magma");
            field_189878_dg = func_180383_a("nether_wart_block");
            field_189879_dh = func_180383_a("red_nether_brick");
            field_189880_di = func_180383_a("bone_block");
            field_189881_dj = func_180383_a("structure_void");
            field_190976_dk = func_180383_a("observer");
            field_190977_dl = func_180383_a("white_shulker_box");
            field_190978_dm = func_180383_a("orange_shulker_box");
            field_190979_dn = func_180383_a("magenta_shulker_box");
            field_190980_do = func_180383_a("light_blue_shulker_box");
            field_190981_dp = func_180383_a("yellow_shulker_box");
            field_190982_dq = func_180383_a("lime_shulker_box");
            field_190983_dr = func_180383_a("pink_shulker_box");
            field_190984_ds = func_180383_a("gray_shulker_box");
            field_190985_dt = func_180383_a("silver_shulker_box");
            field_190986_du = func_180383_a("cyan_shulker_box");
            field_190987_dv = func_180383_a("purple_shulker_box");
            field_190988_dw = func_180383_a("blue_shulker_box");
            field_190989_dx = func_180383_a("brown_shulker_box");
            field_190990_dy = func_180383_a("green_shulker_box");
            field_190991_dz = func_180383_a("red_shulker_box");
            field_190975_dA = func_180383_a("black_shulker_box");
            field_192427_dB = func_180383_a("white_glazed_terracotta");
            field_192428_dC = func_180383_a("orange_glazed_terracotta");
            field_192429_dD = func_180383_a("magenta_glazed_terracotta");
            field_192430_dE = func_180383_a("light_blue_glazed_terracotta");
            field_192431_dF = func_180383_a("yellow_glazed_terracotta");
            field_192432_dG = func_180383_a("lime_glazed_terracotta");
            field_192433_dH = func_180383_a("pink_glazed_terracotta");
            field_192434_dI = func_180383_a("gray_glazed_terracotta");
            field_192435_dJ = func_180383_a("silver_glazed_terracotta");
            field_192436_dK = func_180383_a("cyan_glazed_terracotta");
            field_192437_dL = func_180383_a("purple_glazed_terracotta");
            field_192438_dM = func_180383_a("blue_glazed_terracotta");
            field_192439_dN = func_180383_a("brown_glazed_terracotta");
            field_192440_dO = func_180383_a("green_glazed_terracotta");
            field_192441_dP = func_180383_a("red_glazed_terracotta");
            field_192442_dQ = func_180383_a("black_glazed_terracotta");
            field_192443_dR = func_180383_a("concrete");
            field_192444_dS = func_180383_a("concrete_powder");
            field_185779_df = func_180383_a("structure_block");
            Blocks.field_185780_dg.clear();
        }
    }
}
