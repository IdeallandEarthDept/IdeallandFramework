local outFile = nil;
local modName = "idealland";
local blockName = "grid_normal";

local godNames = {
	"地母，地与暗之神",
	"火哥，火、探索、烹饪与淘汰之神",
	"新婴，生命与现在之神",
	"承翁，土与坚忍之神",
	"坎妹，水与魔法之神",
	"蛊娘，毒之神",
	"雄体，雄性与战争之神",
	"坚少，钢铁、劳动与工具之神",
	"慈老，岩石，矿物与慈悲之神",
	"雌身，雌性与和平之神",
	"林姑，树木之神",
	"贵王，黄金与权力之神",
	"行妪，风与敏捷之神",
	"先祖，死与过去之神",
	"炎叔，熔岩、冰、破坏与背叛之神",
	"天父，天与光之神",
}

local function GenModelBlockItem()
	local path = string.format("1.txt");
	outFile = io.open(path,"w");

--	for i = 0, 100 do
--		outFile:write(string.format('new BlockBase("ch%d", Material.CLAY).setCreativeTab(ModCreativeTab.MAIN_TAB),\n',i));
--	end

	local list = {
		"harp",
		"basedrum",
		"snare",
		"hat",
		"bass",
		"flute",
		"bell",
		"guitar",
		"chime",
		"xylophone"
	}

	for i,key in ipairs(list) do
		--outFile:write(string.format('tile.dungeon_brick_%d.name=Dungeon Brick Block\n',i));
		--outFile:write(string.format('"%s":{"category": "blocks","subtitle": "%s","sounds": [{"name":  "noteblockne:%s", "stream":  true}]},\n',key,key,key));
		--		outFile:write(string.format('item\n',key,key));
	end
--	local tp = {'a','b','c','d','e','f','g','h','i','j','k'}
--
	for i = 0, 15 do
		outFile:write(string.format("item.basic16rune_%s.name=%s号符文\n", i, i));
		outFile:write(string.format("item.basic16rune_%d.desc=合成材料，其数为%d。%s的符文。\n\n", i, i, godNames[i + 1]));
	end

	outFile:close();
end

GenModelBlockItem();

print("Done");
