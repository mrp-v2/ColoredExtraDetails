package io.github.mrp_v2.coloredextradetails.util;

import com.jamesz.extradetails.Registry.Blocks;
import io.github.mrp_v2.coloredextradetails.ColoredExtraDetails;
import mrp_v2.additionalcolors.AdditionalColors;
import mrp_v2.additionalcolors.api.block_properties.BlockBasedPropertiesProvider;
import mrp_v2.additionalcolors.api.colored_block_data.AbstractColoredBlockData;
import mrp_v2.additionalcolors.api.colored_block_data.ColoredBlockData;
import mrp_v2.additionalcolors.api.colored_block_data.ColoredBlockDataHandler;
import mrp_v2.additionalcolors.api.colored_block_data.ColoredRotatedPillarBlockData;
import mrp_v2.mrplibrary.datagen.providers.TextureProvider;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class ObjectHolder
{
    public static final ColoredBlockDataHandler COLORED_BLOCK_DATA_HANDLER;
    public static final ItemGroup MAIN_ITEM_GROUP = new ItemGroup(ColoredExtraDetails.ID)
    {
        private final RegistryObject<Item> iconItem = RegistryObject
                .of(new ResourceLocation(ColoredExtraDetails.ID, "green_polished_cobblestone"), ForgeRegistries.ITEMS);

        @Override public ItemStack createIcon()
        {
            return new ItemStack(iconItem.get());
        }
    };

    static
    {
        COLORED_BLOCK_DATA_HANDLER = new ColoredBlockDataHandler(ColoredExtraDetails.ID, MAIN_ITEM_GROUP)
        {
            @Override protected LanguageGenerator makeLanguageGenerator(DataGenerator gen, String modid)
            {
                return new LanguageGenerator(gen, modid)
                {
                    @Override protected void addTranslations()
                    {
                        super.addTranslations();
                        add(ObjectHolder.MAIN_ITEM_GROUP, ColoredExtraDetails.DISPLAY_NAME);
                    }
                };
            }
        };
        addColorizedBlockDatas();
    }

    private static void addColorizedBlockDatas()
    {
        // Polished Cobblestone (Slab, Stairs, Wall)
        new ColoredBlockData(Blocks.POLISHED_COBBLESTONE, COLORED_BLOCK_DATA_HANDLER)
                .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.COBBLESTONE))
                .add(COLORED_BLOCK_DATA_HANDLER).makeSlabBlock(Blocks.POLISHED_COBBLE_SLAB)
                .add(COLORED_BLOCK_DATA_HANDLER).parent().makeStairsBlock(Blocks.POLISHED_COBBLE_STAIRS)
                .add(COLORED_BLOCK_DATA_HANDLER).parent().makeWallBlock(Blocks.POLISHED_COBBLE_WALL)
                .add(COLORED_BLOCK_DATA_HANDLER).parent();
        // Polished Cobblestone Bricks (Slab, Stairs, Wall)
        new ColoredBlockData(Blocks.POLISHED_COBBLESTONE_BRICKS, COLORED_BLOCK_DATA_HANDLER)
                .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.COBBLESTONE))
                .add(COLORED_BLOCK_DATA_HANDLER).makeSlabBlock(Blocks.POLISHED_COBBLE_BRICK_SLAB)
                .add(COLORED_BLOCK_DATA_HANDLER).parent().makeStairsBlock(Blocks.POLISHED_COBBLE_BRICK_STAIRS)
                .add(COLORED_BLOCK_DATA_HANDLER).parent().makeWallBlock(Blocks.POLISHED_COBBLE_BRICK_WALL)
                .add(COLORED_BLOCK_DATA_HANDLER).parent();
        // Chiseled Cobblestone
        new ColoredBlockData(Blocks.CHISELED_COBBLESTONE, COLORED_BLOCK_DATA_HANDLER)
                .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.COBBLESTONE))
                .add(COLORED_BLOCK_DATA_HANDLER);
        // Chiseled Mossy Cobblestone
        new ColoredBlockData(Blocks.CHISELED_MOSSY_COBBLESTONE, COLORED_BLOCK_DATA_HANDLER)
                .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.COBBLESTONE))
                .add(COLORED_BLOCK_DATA_HANDLER);
        final Consumer4<AbstractColoredBlockData<?>, RegistryObject<Block>, RegistryObject<Block>, RegistryObject<Block>>
                slabStairsWallMaker =
                (baseBlockData, slabObject, stairsObject, wallObject) -> baseBlockData.makeSlabBlock(slabObject)
                        .add(COLORED_BLOCK_DATA_HANDLER).parent().makeStairsBlock(stairsObject)
                        .add(COLORED_BLOCK_DATA_HANDLER).parent().makeWallBlock(wallObject)
                        .add(COLORED_BLOCK_DATA_HANDLER).parent();
        final Consumer8<Block, RegistryObject<Block>, RegistryObject<Block>, RegistryObject<Block>, RegistryObject<Block>, RegistryObject<Block>, RegistryObject<Block>, RegistryObject<Block>>
                polishedStoneVariantColorer =
                (basePolishedBlock, polishedWallObject, polishedBricksObject, polishedBrickSlabObject, polishedBrickStairsObject, polishedBrickWallObject, polishedPillarObject, chiseledPolishedObject) ->
                {
                    slabStairsWallMaker.accept(new ColoredBlockData(polishedBricksObject, COLORED_BLOCK_DATA_HANDLER)
                                    .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(basePolishedBlock))
                                    .add(COLORED_BLOCK_DATA_HANDLER), polishedBrickSlabObject, polishedBrickStairsObject,
                            polishedBrickWallObject);
                    new ColoredRotatedPillarBlockData(polishedPillarObject, COLORED_BLOCK_DATA_HANDLER)
                    {
                        @Override protected String getSideSuffix()
                        {
                            return "";
                        }
                    }.setBlockPropertiesProvider(new BlockBasedPropertiesProvider(basePolishedBlock))
                            .add(COLORED_BLOCK_DATA_HANDLER);
                    new ColoredRotatedPillarBlockData(chiseledPolishedObject, COLORED_BLOCK_DATA_HANDLER)
                    {
                        @Override protected String getSideSuffix()
                        {
                            return "";
                        }
                    }.setBlockPropertiesProvider(new BlockBasedPropertiesProvider(basePolishedBlock))
                            .add(COLORED_BLOCK_DATA_HANDLER);
                };
        // (Polished Andesite) (Wall, Bricks, Brick Slab, Brick Stairs, Brick Wall, Pillar, Chiseled)
        polishedStoneVariantColorer.accept(net.minecraft.block.Blocks.POLISHED_ANDESITE, Blocks.POLISHED_ANDESITE_WALL,
                Blocks.POLISHED_ANDESITE_BRICKS, Blocks.POLISHED_ANDESITE_BRICK_SLAB,
                Blocks.POLISHED_ANDESITE_BRICK_STAIRS, Blocks.POLISHED_ANDESITE_BRICK_WALL,
                Blocks.POLISHED_ANDESITE_PILLAR, Blocks.CHISELED_POLISHED_ANDESITE);
        // (Polished Granite) (Wall, Bricks, Brick Slab, Brick Stairs, Brick Wall, Pillar, Chiseled)
        polishedStoneVariantColorer.accept(net.minecraft.block.Blocks.POLISHED_GRANITE, Blocks.POLISHED_GRANITE_WALL,
                Blocks.POLISHED_GRANITE_BRICKS, Blocks.POLISHED_GRANITE_BRICK_SLAB,
                Blocks.POLISHED_GRANITE_BRICK_STAIRS, Blocks.POLISHED_GRANITE_BRICK_WALL,
                Blocks.POLISHED_GRANITE_PILLAR, Blocks.CHISELED_POLISHED_GRANITE);
        // (Polished Diorite) (Wall, Bricks, Brick Slab, Brick Stairs, Brick Wall, Pillar, Chiseled)
        polishedStoneVariantColorer.accept(net.minecraft.block.Blocks.POLISHED_DIORITE, Blocks.POLISHED_DIORITE_WALL,
                Blocks.POLISHED_DIORITE_BRICKS, Blocks.POLISHED_DIORITE_BRICK_SLAB,
                Blocks.POLISHED_DIORITE_BRICK_STAIRS, Blocks.POLISHED_DIORITE_BRICK_WALL,
                Blocks.POLISHED_DIORITE_PILLAR, Blocks.CHISELED_POLISHED_DIORITE);
        // (Smooth Stone) (Stairs, Wall)
        mrp_v2.additionalcolors.util.ObjectHolder.COLORED_BLOCK_DATA_HANDLER
                .getColoredBlockData(net.minecraft.block.Blocks.SMOOTH_STONE.getRegistryName())
                .makeStairsBlock(Blocks.SMOOTH_STONE_STAIRS).add(COLORED_BLOCK_DATA_HANDLER).parent()
                .makeWallBlock(Blocks.SMOOTH_STONE_WALL).add(COLORED_BLOCK_DATA_HANDLER).parent();
        // Smooth Stone Bricks (Slab, Stairs, Wall)
        slabStairsWallMaker.accept(new ColoredBlockData(Blocks.SMOOTH_STONE_BRICKS, COLORED_BLOCK_DATA_HANDLER)
                        .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.SMOOTH_STONE))
                        .add(COLORED_BLOCK_DATA_HANDLER), Blocks.SMOOTH_STONE_BRICK_SLAB, Blocks.SMOOTH_STONE_BRICK_STAIRS,
                Blocks.SMOOTH_STONE_BRICK_WALL);
        // Chiseled Smooth Stone
        new ColoredBlockData(Blocks.CHISELED_SMOOTH_STONE, COLORED_BLOCK_DATA_HANDLER)
                .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.SMOOTH_STONE))
                .add(COLORED_BLOCK_DATA_HANDLER);
        // (Smooth Sandstone) (Wall)
        mrp_v2.additionalcolors.util.ObjectHolder.COLORED_BLOCK_DATA_HANDLER
                .getColoredBlockData(net.minecraft.block.Blocks.SMOOTH_SANDSTONE.getRegistryName())
                .makeWallBlock(Blocks.SMOOTH_SANDSTONE_WALL).add(COLORED_BLOCK_DATA_HANDLER);
        // Sandstone Bricks (Slab, Stairs, Wall)
        slabStairsWallMaker.accept(new ColoredBlockData(Blocks.SANDSTONE_BRICKS, COLORED_BLOCK_DATA_HANDLER)
                        .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.SANDSTONE))
                        .add(COLORED_BLOCK_DATA_HANDLER), Blocks.SANDSTONE_BRICK_SLAB, Blocks.SANDSTONE_BRICK_STAIRS,
                Blocks.SANDSTONE_BRICK_WALL);
        // Sandstone Pillar
        new ColoredRotatedPillarBlockData(Blocks.SANDSTONE_PILLAR, COLORED_BLOCK_DATA_HANDLER)
        {
            @Override protected String getSideSuffix()
            {
                return "";
            }
        }.setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.SANDSTONE))
                .add(COLORED_BLOCK_DATA_HANDLER);
        // (Prismarine Bricks) (Wall)
        mrp_v2.additionalcolors.util.ObjectHolder.COLORED_BLOCK_DATA_HANDLER
                .getColoredBlockData(net.minecraft.block.Blocks.PRISMARINE_BRICKS.getRegistryName())
                .makeWallBlock(Blocks.PRISMARINE_BRICK_WALL).add(COLORED_BLOCK_DATA_HANDLER);
        // Polished Prismarine (Slab, Stairs, Wall)
        slabStairsWallMaker.accept(new ColoredBlockData(Blocks.POLISHED_PRISMARINE, COLORED_BLOCK_DATA_HANDLER)
                        .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.PRISMARINE))
                        .add(COLORED_BLOCK_DATA_HANDLER), Blocks.POLISHED_PRISMARINE_SLAB, Blocks.POLISHED_PRISMARINE_STAIRS,
                Blocks.POLISHED_PRISMARINE_WALL);
        // Polished Prismarine Pillar
        new ColoredRotatedPillarBlockData(Blocks.POLISHED_PRISMARINE_PILLAR, COLORED_BLOCK_DATA_HANDLER)
        {
            @Override protected String getSideSuffix()
            {
                return "";
            }
        }.setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.PRISMARINE))
                .add(COLORED_BLOCK_DATA_HANDLER);
        // Chiseled Polished Prismarine
        new ColoredBlockData(Blocks.CHISELED_POLISHED_PRISMARINE, COLORED_BLOCK_DATA_HANDLER)
                .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.PRISMARINE))
                .add(COLORED_BLOCK_DATA_HANDLER);
        // (Dark Prismarine) (Wall)
        mrp_v2.additionalcolors.util.ObjectHolder.COLORED_BLOCK_DATA_HANDLER
                .getColoredBlockData(net.minecraft.block.Blocks.DARK_PRISMARINE.getRegistryName())
                .makeWallBlock(Blocks.DARK_PRISMARINE_WALL).add(COLORED_BLOCK_DATA_HANDLER);
        // Dark Prismarine Bricks (Slab, Stairs, Wall)
        slabStairsWallMaker.accept(new ColoredBlockData(Blocks.DARK_PRISMARINE_BRICKS, COLORED_BLOCK_DATA_HANDLER)
                        .setBlockPropertiesProvider(
                                new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.DARK_PRISMARINE))
                        .add(COLORED_BLOCK_DATA_HANDLER), Blocks.DARK_PRISMARINE_BRICK_SLAB,
                Blocks.DARK_PRISMARINE_BRICK_STAIRS, Blocks.DARK_PRISMARINE_BRICK_WALL);
        // Chiseled Dark Prismarine
        new ColoredRotatedPillarBlockData(Blocks.CHISELED_DARK_PRISMARINE, COLORED_BLOCK_DATA_HANDLER)
        {
            @Override protected String getSideSuffix()
            {
                return "";
            }
        }.setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.DARK_PRISMARINE))
                .add(COLORED_BLOCK_DATA_HANDLER);
        // (Obsidian) (Slab, Stairs, Wall)
        mrp_v2.additionalcolors.util.ObjectHolder.COLORED_BLOCK_DATA_HANDLER
                .getColoredBlockData(net.minecraft.block.Blocks.OBSIDIAN.getRegistryName())
                .makeSlabBlock(Blocks.OBSIDIAN_SLAB).add(COLORED_BLOCK_DATA_HANDLER).parent()
                .makeStairsBlock(Blocks.OBSIDIAN_STAIRS).add(COLORED_BLOCK_DATA_HANDLER).parent()
                .makeWallBlock(Blocks.OBSIDIAN_WALL).add(COLORED_BLOCK_DATA_HANDLER).parent();
        // Polished Obsidian (Slab, Stairs, Wall)
        slabStairsWallMaker.accept(new ColoredBlockData(Blocks.POLISHED_OBSIDIAN, COLORED_BLOCK_DATA_HANDLER)
                {
                    @Override protected double getLevelAdjustment()
                    {
                        return 0.5D;
                    }
                }.setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.OBSIDIAN))
                        .add(COLORED_BLOCK_DATA_HANDLER), Blocks.POLISHED_OBSIDIAN_SLAB, Blocks.POLISHED_OBSIDIAN_STAIRS,
                Blocks.POLISHED_OBSIDIAN_WALL);
        // Obsidian Pillar
        new ColoredRotatedPillarBlockData(Blocks.OBSIDIAN_PILLAR, COLORED_BLOCK_DATA_HANDLER)
        {
            @Override protected String getSideSuffix()
            {
                return "";
            }
        }.setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.OBSIDIAN))
                .add(COLORED_BLOCK_DATA_HANDLER);
        // Chiseled Obsidian
        new ColoredBlockData(Blocks.CHISELED_OBSIDIAN, COLORED_BLOCK_DATA_HANDLER)
                .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.OBSIDIAN))
                .add(COLORED_BLOCK_DATA_HANDLER);
        // (Netherrack) (Slab, Stairs, Wall)
        mrp_v2.additionalcolors.util.ObjectHolder.COLORED_BLOCK_DATA_HANDLER
                .getColoredBlockData(net.minecraft.block.Blocks.NETHERRACK.getRegistryName())
                .makeSlabBlock(Blocks.NETHERRACK_SLAB).add(COLORED_BLOCK_DATA_HANDLER).parent()
                .makeStairsBlock(Blocks.NETHERRACK_STAIRS).add(COLORED_BLOCK_DATA_HANDLER).parent()
                .makeWallBlock(Blocks.NETHERRACK_WALL).add(COLORED_BLOCK_DATA_HANDLER).parent();
        // Polished Netherrack (Slab, Stairs, Wall)
        new ColoredBlockData(Blocks.POLISHED_NETHERRACK, COLORED_BLOCK_DATA_HANDLER)
                .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.NETHERRACK))
                .add(COLORED_BLOCK_DATA_HANDLER).makeSlabBlock(Blocks.POLISHED_NETHERRACK_SLAB)
                .add(COLORED_BLOCK_DATA_HANDLER).parent().makeStairsBlock(Blocks.POLISHED_NETHERRACK_STAIRS)
                .add(COLORED_BLOCK_DATA_HANDLER).parent().makeWallBlock(Blocks.POLISHED_NETHERRACK_WALL)
                .add(COLORED_BLOCK_DATA_HANDLER).parent();
        // Netherrack Bricks (Slab, Stairs, Wall)
        new ColoredBlockData(Blocks.NETHERRACK_BRICKS, COLORED_BLOCK_DATA_HANDLER)
                .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.NETHERRACK))
                .add(COLORED_BLOCK_DATA_HANDLER).makeSlabBlock(Blocks.NETHERRACK_BRICK_SLAB)
                .add(COLORED_BLOCK_DATA_HANDLER).parent().makeStairsBlock(Blocks.NETHERRACK_BRICK_STAIRS)
                .add(COLORED_BLOCK_DATA_HANDLER).parent().makeWallBlock(Blocks.NETHERRACK_BRICK_WALL)
                .add(COLORED_BLOCK_DATA_HANDLER).parent();
        // Chiseled Netherrack
        new ColoredBlockData(Blocks.CHISELED_NETHERRACK, COLORED_BLOCK_DATA_HANDLER)
                .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.NETHERRACK))
                .add(COLORED_BLOCK_DATA_HANDLER);
        // (Quartz) (Wall)
        mrp_v2.additionalcolors.util.ObjectHolder.COLORED_BLOCK_DATA_HANDLER
                .getColoredBlockData(net.minecraft.block.Blocks.QUARTZ_BLOCK.getRegistryName())
                .makeWallBlock(Blocks.QUARTZ_WALL).add(COLORED_BLOCK_DATA_HANDLER).parent();
        // (Smooth Quartz) (Wall)
        mrp_v2.additionalcolors.util.ObjectHolder.COLORED_BLOCK_DATA_HANDLER
                .getColoredBlockData(net.minecraft.block.Blocks.SMOOTH_QUARTZ.getRegistryName())
                .makeWallBlock(Blocks.SMOOTH_QUARTZ_WALL).add(COLORED_BLOCK_DATA_HANDLER).parent();
        // (Basalt) (Slab, Stairs, Wall)
        mrp_v2.additionalcolors.util.ObjectHolder.COLORED_BLOCK_DATA_HANDLER
                .getColoredBlockData(net.minecraft.block.Blocks.BASALT.getRegistryName())
                .makeSlabBlock(Blocks.BASALT_SLAB).add(COLORED_BLOCK_DATA_HANDLER).parent()
                .makeStairsBlock(Blocks.BASALT_STAIRS).add(COLORED_BLOCK_DATA_HANDLER).parent()
                .makeWallBlock(Blocks.BASALT_WALL).add(COLORED_BLOCK_DATA_HANDLER).parent();
        // Basalt Core
        new ColoredRotatedPillarBlockData(Blocks.BASALT_CORE, COLORED_BLOCK_DATA_HANDLER)
        {
            @Override public void makeTextureGenerationPromises(TextureProvider generator)
            {
            }

            @Override protected ResourceLocation getEndTextureLoc(boolean base)
            {
                return getSideTextureLoc(base);
            }

            @Override protected ResourceLocation getSideTextureLoc(boolean base)
            {
                return new ResourceLocation(AdditionalColors.ID, "block/basalt_top");
            }

            @Override
            public void registerTextures(TextureProvider generator, TextureProvider.FinishedTextureConsumer consumer)
            {
            }
        }.setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.BASALT))
                .add(COLORED_BLOCK_DATA_HANDLER);
        // Basalt Shell
        new ColoredRotatedPillarBlockData(Blocks.BASALT_SHELL, COLORED_BLOCK_DATA_HANDLER)
        {
            @Override public void makeTextureGenerationPromises(TextureProvider generator)
            {
            }

            @Override protected ResourceLocation getEndTextureLoc(boolean base)
            {
                return getSideTextureLoc(base);
            }

            @Override
            public void registerTextures(TextureProvider generator, TextureProvider.FinishedTextureConsumer consumer)
            {
            }

            @Override protected ResourceLocation getSideTextureLoc(boolean base)
            {
                return new ResourceLocation(AdditionalColors.ID, "block/basalt_side");
            }
        }.setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.BASALT))
                .add(COLORED_BLOCK_DATA_HANDLER);
        // (Polished Basalt) (Slab, Stairs, Wall)
        mrp_v2.additionalcolors.util.ObjectHolder.COLORED_BLOCK_DATA_HANDLER
                .getColoredBlockData(net.minecraft.block.Blocks.POLISHED_BASALT.getRegistryName())
                .makeSlabBlock(Blocks.POLISHED_BASALT_SLAB).add(COLORED_BLOCK_DATA_HANDLER).parent()
                .makeStairsBlock(Blocks.POLISHED_BASALT_STAIRS).add(COLORED_BLOCK_DATA_HANDLER).parent()
                .makeWallBlock(Blocks.POLISHED_BASALT_WALL).add(COLORED_BLOCK_DATA_HANDLER).parent();
        // Polished Basalt Core
        new ColoredRotatedPillarBlockData(Blocks.POLISHED_BASALT_CORE, COLORED_BLOCK_DATA_HANDLER)
        {
            @Override public void makeTextureGenerationPromises(TextureProvider generator)
            {
            }

            @Override protected ResourceLocation getEndTextureLoc(boolean base)
            {
                return getSideTextureLoc(base);
            }

            @Override protected ResourceLocation getSideTextureLoc(boolean base)
            {
                return new ResourceLocation(AdditionalColors.ID, "block/polished_basalt_top");
            }

            @Override
            public void registerTextures(TextureProvider generator, TextureProvider.FinishedTextureConsumer consumer)
            {
            }
        }.setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.BASALT))
                .add(COLORED_BLOCK_DATA_HANDLER);
        // Polished Basalt Shell
        new ColoredRotatedPillarBlockData(Blocks.POLISHED_BASALT_SHELL, COLORED_BLOCK_DATA_HANDLER)
        {
            @Override public void makeTextureGenerationPromises(TextureProvider generator)
            {
            }

            @Override protected ResourceLocation getEndTextureLoc(boolean base)
            {
                return getSideTextureLoc(base);
            }

            @Override
            public void registerTextures(TextureProvider generator, TextureProvider.FinishedTextureConsumer consumer)
            {
            }

            @Override protected ResourceLocation getSideTextureLoc(boolean base)
            {
                return new ResourceLocation(AdditionalColors.ID, "block/polished_basalt_side");
            }
        }.setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.BASALT))
                .add(COLORED_BLOCK_DATA_HANDLER);
        // (End Stone) (Slab, Stairs Wall)
        mrp_v2.additionalcolors.util.ObjectHolder.COLORED_BLOCK_DATA_HANDLER
                .getColoredBlockData(net.minecraft.block.Blocks.END_STONE.getRegistryName())
                .makeSlabBlock(Blocks.END_STONE_SLAB).add(COLORED_BLOCK_DATA_HANDLER).parent()
                .makeStairsBlock(Blocks.END_STONE_STAIRS).add(COLORED_BLOCK_DATA_HANDLER).parent()
                .makeWallBlock(Blocks.END_STONE_WALL).add(COLORED_BLOCK_DATA_HANDLER).parent();
        // Polished End Stone (Slab, Stairs, Wall)
        new ColoredBlockData(Blocks.POLISHED_END_STONE, COLORED_BLOCK_DATA_HANDLER)
                .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.END_STONE))
                .add(COLORED_BLOCK_DATA_HANDLER).makeSlabBlock(Blocks.POLISHED_END_STONE_SLAB)
                .add(COLORED_BLOCK_DATA_HANDLER).parent().makeStairsBlock(Blocks.POLISHED_END_STONE_STAIRS)
                .add(COLORED_BLOCK_DATA_HANDLER).parent().makeWallBlock(Blocks.POLISHED_END_STONE_WALL)
                .add(COLORED_BLOCK_DATA_HANDLER).parent();
        // End Stone Pillar
        new ColoredRotatedPillarBlockData(Blocks.END_STONE_PILLAR, COLORED_BLOCK_DATA_HANDLER)
        {
            @Override protected String getSideSuffix()
            {
                return "";
            }
        }.setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.END_STONE))
                .add(COLORED_BLOCK_DATA_HANDLER);
        // Chiseled End Stone
        new ColoredBlockData(Blocks.CHISELED_END_STONE, COLORED_BLOCK_DATA_HANDLER)
                .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.END_STONE))
                .add(COLORED_BLOCK_DATA_HANDLER);
        // (Purpur) (Wall)
        mrp_v2.additionalcolors.util.ObjectHolder.COLORED_BLOCK_DATA_HANDLER
                .getColoredBlockData(net.minecraft.block.Blocks.PURPUR_BLOCK.getRegistryName())
                .makeWallBlock(Blocks.PURPUR_WALL).add(COLORED_BLOCK_DATA_HANDLER).parent();
        // Polished Purpur (Slab, Stairs, Wall)
        new ColoredBlockData(Blocks.POLISHED_PURPUR, COLORED_BLOCK_DATA_HANDLER)
                .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.PURPUR_BLOCK))
                .add(COLORED_BLOCK_DATA_HANDLER).makeSlabBlock(Blocks.POLISHED_PURPUR_SLAB)
                .add(COLORED_BLOCK_DATA_HANDLER).parent().makeStairsBlock(Blocks.POLISHED_PURPUR_STAIRS)
                .add(COLORED_BLOCK_DATA_HANDLER).parent().makeWallBlock(Blocks.POLISHED_PURPUR_WALL)
                .add(COLORED_BLOCK_DATA_HANDLER).parent();
        // Purpur Bricks (Slab, Stairs, Wall)
        new ColoredBlockData(Blocks.PURPUR_BRICKS, COLORED_BLOCK_DATA_HANDLER)
                .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.PURPUR_BLOCK))
                .add(COLORED_BLOCK_DATA_HANDLER).makeSlabBlock(Blocks.PURPUR_BRICK_SLAB).add(COLORED_BLOCK_DATA_HANDLER)
                .parent().makeStairsBlock(Blocks.PURPUR_BRICK_STAIRS).add(COLORED_BLOCK_DATA_HANDLER).parent()
                .makeWallBlock(Blocks.PURPUR_BRICK_WALL).add(COLORED_BLOCK_DATA_HANDLER).parent();
        // Chiseled Purpur
        new ColoredBlockData(Blocks.CHISELED_PURPUR, COLORED_BLOCK_DATA_HANDLER)
                .setBlockPropertiesProvider(new BlockBasedPropertiesProvider(net.minecraft.block.Blocks.PURPUR_BLOCK))
                .add(COLORED_BLOCK_DATA_HANDLER);
    }

    public static void registerListeners(IEventBus modEventBus)
    {
        COLORED_BLOCK_DATA_HANDLER.register(modEventBus);
    }
}
