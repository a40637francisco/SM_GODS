function ex1b()

close all;

MyFolderInfo = dir('..\TestFilesSM');

entropias = zeros(1,length(MyFolderInfo)-2);
removedRates = zeros(1,length(MyFolderInfo)-2);
original_file_names = {};
filesInitSize = zeros(1,length(MyFolderInfo)-2);

%Compress to Huff
index = 1;
for i = 1 : length(MyFolderInfo)
    file_name = MyFolderInfo(i).name; 
        if(strcmp(file_name,'.') || strcmp(file_name,'..')) 
            continue;
        end
    index = index + 1;    
        
        
    original_file_names{index} = file_name;    
    file_name_output = [ file_name 'out' ];
    initial_length = MyFolderInfo(i).bytes;
    
    %Comprime em Huffman%
    exe = ['.\TestCodersSM\Huff64_Enc.exe ' '..\TestFilesSM\' file_name ' ' '.\Outputs\' file_name_output ];
    
    system(exe);
    
    %Buscar bytes do output%
    file = dir(['.\Outputs\' file_name_output]);
    final_length = file.bytes;
    
    x = file2Vector(['..\TestFilesSM\' file_name]);
    f = hist(x,256);
    p = f / sum(f);
    
    p( p == 0) = 1;
    H = - sum(p .* log2(p));
    
    removed_rate = (1 - final_length / initial_length ) * 100;
    ceil(removed_rate)
    
    entropias(index) = H;
    removedRates(index) = removed_rate;
    filesInitSize(index) = initial_length;
end
    
figure
for i=1 : length(entropias)
    plot(entropias(i),removedRates(i),'.');
    hold on;
end

% Taxas de compressão negativas significam que os ficheiros originais ja
% tinham entropia muito elevada, fazendo com que o ficheiro codificado
% fique maior.

MyFolderInfo = dir('./rars');
index = 1;
for i = 1 : length(MyFolderInfo)
    file_name = MyFolderInfo(i).name; 
        if(strcmp(file_name,'.') || strcmp(file_name,'..')) 
            continue;
        end
    index = index + 1;    
    final_length = MyFolderInfo(i).bytes;
    
    removed_rate = (1 - final_length / filesInitSize(index) ) * 100;
    ceil(removed_rate)
    
    removedRates(index) = removed_rate;
  % c(i) = file_name_output;
      
end

figure
    for i=1 : length(entropias)
        plot(entropias(i),removedRates(i),original_file_names{i});
        hold on;
    end

end