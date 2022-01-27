local outFile = nil;
local modName = "idealland";
local blockName = "grid_normal";

local inputFile = "potion_input.txt"

local function GenModelBlockItem()
	local path = string.format("potion_output.txt");
	outFile = io.open(path,"w");

	local storedLines = {};

	for line in io.lines(inputFile) do
		if (string.len(line)>5) then
			table.insert(storedLines, line);

		end
	end

	for i,line in ipairs(storedLines) do
		outFile:write(line.."\n");
		print(line);
	end
	outFile:write("\n");

	for i,line in ipairs(storedLines) do
		outFile:write("potion.effect."..line.."\n");
	end
	outFile:write("\n");

	for i,line in ipairs(storedLines) do
		outFile:write("lingering_potion.effect."..line.."\n");
	end
	outFile:write("\n");

	for i,line in ipairs(storedLines) do
		outFile:write("splash_potion.effect."..line.."\n");
	end

	outFile:write("\n");
	for i,line in ipairs(storedLines) do
		outFile:write("tipped_arrow.effect."..line.."\n");
	end
--	local tp = {'a','b','c','d','e','f','g','h','i','j','k'}
--
-- 	for i = 1, 12 do
--		local cur_tp = tp[i];
--		outFile:write("public static final ItemBase[] M_"..cur_tp.." =\n");
--		outFile:write("		new ItemBase[]{\n");
--		outFile:write('				new ItemBase("m_'..cur_tp..'_1"),\n');
--		outFile:write('				new ItemBase("m_'..cur_tp..'_2"),\n');
--		outFile:write('				new ItemBase("m_'..cur_tp..'_3"),\n');
--		outFile:write('				new ItemBase("m_'..cur_tp..'_4"),\n');
--		outFile:write('		};\n\n');
--
--	end

	outFile:close();
end

GenModelBlockItem();

print("Done");
