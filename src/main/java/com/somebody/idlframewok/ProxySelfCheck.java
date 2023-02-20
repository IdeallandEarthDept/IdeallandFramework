package com.somebody.idlframewok;

import com.somebody.idlframewok.proxy.ClientProxy;
import com.somebody.idlframewok.proxy.ServerProxy;
import com.somebody.idlframewok.util.Reference;
import net.minecraftforge.fml.common.FMLLog;

public class ProxySelfCheck {
    public ProxySelfCheck(){
        if(Reference.CLIENT_PROXY_CLASS.equals(ClientProxy.class.getName()) && Reference.SERVER_PROXY_CLASS.equals(ServerProxy.class.getName())){
            FMLLog.getLogger().error("{IDF}这不是个错误！：");
            FMLLog.getLogger().error("恭喜你成功配置proxy！");
            FMLLog.getLogger().error("你需要删除提示用的相关代码：");
            FMLLog.getLogger().error(String.format("%s",this.getClass().getName()));

            FMLLog.getLogger().error(String.format("%s.deleteMe",IdlFramework.class.getName()));
        }
        else {
            FMLLog.getLogger().error("{IDF}这是个致命的错误！：");
            FMLLog.getLogger().error("你的proxy有问题！");
            FMLLog.getLogger().error("这个链接可以解决你的问题。");
            FMLLog.getLogger().error("https://www.bilibili.com/read/cv17173344?spm_id_from=333.999.0.0");
            FMLLog.getLogger().error("正确的：");
            FMLLog.getLogger().error("CLIENT_PROXY_CLASS=\""+ClientProxy.class.getName()+"\"");
            FMLLog.getLogger().error("SERVER_PROXY_CLASS=\""+ServerProxy.class.getName()+"\"");
        }
    }
}
