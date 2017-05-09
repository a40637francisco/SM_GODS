function [removedRates]  = rarsFunction(filesInitSize)

removedRates = zeros(1,15);

MyFolderInfo = dir('./rars');
index = 1;
for i = 1 : length(MyFolderInfo)
    file_name = MyFolderInfo(i).name; 
        if(strcmp(file_name,'.') || strcmp(file_name,'..')) 
            continue;
        end
 
    final_length = MyFolderInfo(i).bytes;
    
    removed_rate = (1 - final_length / filesInitSize(index) ) * 100;
    ceil(removed_rate)
    
    removedRates(index) = removed_rate;
    index = index + 1;   
end


end