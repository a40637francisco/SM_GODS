function [H,removed_rate,initial_length] = huff(path,fileName,fileSize,index)
  
    file_name_output = [ 'out_' fileName ];
    initial_length = fileSize;
    
    %Comprime em Huffman%
    exe = ['.\TestCodersSM\Huff64_Enc.exe ' path ' ' '.\Outputs\' file_name_output ];
    
    system(exe);
    
    %Buscar bytes do output%
    file = dir(['.\Outputs\' file_name_output]);
    final_length = file.bytes;
    
    x = file2Vector(path);
    f = hist(x,256);
    p = f / sum(f);
    
    p( p == 0) = 1;
    H = - sum(p .* log2(p));
    
    removed_rate = (1 - final_length / initial_length ) * 100;
    ceil(removed_rate)

end