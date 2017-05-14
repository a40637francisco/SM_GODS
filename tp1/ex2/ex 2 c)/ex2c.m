function ex2c( )
close all


%Contated Files Huff 
concatedFilesFolderInfo = dir('./ConcatedFiles');
concatedFilesSize = zeros(1,length(concatedFilesFolderInfo) - 2); 
concatedHuffRemovedRates = zeros(1, length(concatedFilesFolderInfo) - 2);

index = 1;
for i = 1 : length(concatedFilesFolderInfo)
   fileName = concatedFilesFolderInfo(i).name;
   inputFileLength = concatedFilesFolderInfo(i).bytes;
   if(strcmp(fileName,'.') || strcmp(fileName,'..')) 
        continue;
   end
  
   path = strcat('./ConcatedFiles/', fileName);
   
   file_name_output = ['Huff' fileName ];
   exe = ['.\TestCodersSM\Huff64_Enc.exe ' path ' ' '.\ConcatedOutputs\' file_name_output ];
   system(exe);
   
   concatedHuffOutput = dir(['.\ConcatedOutputs\' file_name_output]);
   concatedHuffOutputLength = concatedHuffOutput.bytes;   
   
   removed_rate = (1 - concatedHuffOutputLength / inputFileLength ) * 100;
   ceil(removed_rate)
   
   concatedFilesSize(index) = inputFileLength;
   concatedHuffRemovedRates(index) = removed_rate;
   index = index + 1;
   
end  


%Concated Files .Rar
index = 1;
concatedRarFiles = dir('./ConcatedRars');
concatedRarRemovedRates = zeros(1, length(concatedRarFiles) - 2);
for i = 1 : length(concatedRarFiles)
    fileName = concatedRarFiles(i).name;
    if(strcmp(fileName,'.') || strcmp(fileName,'..')) 
            continue;
    end
   
    fileLength = concatedRarFiles(i).bytes;
    removedRate = (1 - fileLength / concatedFilesSize(index) ) * 100;
    ceil(removedRate);
    concatedRarRemovedRates(index) = removedRate;
    index = index + 1;
end


%Files Huff

mainFolderInfo = dir('../testFiles');
normalFilesSize = zeros(5, 3);
fileHuffRemovedRates = zeros(1, length(mainFolderInfo) - 3);
index = 1;
for i = 1 : length(mainFolderInfo)
    folderName = mainFolderInfo(i).name;
    if(strcmp(folderName,'.') || strcmp(folderName,'..') || strcmp(folderName, 'Thumbs.db')) 
        continue;
    end
    folderInfo = dir(strcat('../testFiles/', folderName));
    averageRate = 0;
    subIndex = 1;
    for j=1: 7
        fileName = folderInfo(j).name;
        if(strcmp(fileName,'.') || strcmp(fileName,'..')) 
            continue;
        end
        initialFileLength = folderInfo(j).bytes;
        path = strcat('../testFiles/', folderName,'/' ,fileName);
        fileNameOutput = ['Huff' fileName];
        
        exe = ['.\TestCodersSM\Huff64_Enc.exe ' path ' ' '.\NormalOutputs\' folderName '\' fileNameOutput ];
        system(exe); 
        
        fileHuffOutput = dir(['.\NormalOutputs\' folderName '\' fileNameOutput]); 
        fileHuffOutputLength = fileHuffOutput.bytes;

        removedRate = (1 - fileHuffOutputLength / initialFileLength ) * 100;
        ceil(removedRate);
        averageRate = averageRate + removedRate;
        normalFilesSize(subIndex, index) = initialFileLength;
        subIndex = subIndex + 1;
    end
    averageRate = averageRate / 5;
    fileHuffRemovedRates(index) = averageRate;
    index = index + 1;  
end


%Files Rar
index = 1;
normalRarFolder = dir('./NormalRars');
normalRarRemovedRates = zeros(1, length(normalRarFolder) - 2);
for i = 1 : length(normalRarFolder)
    folderName = normalRarFolder(i).name;
    if(strcmp(folderName,'.') || strcmp(folderName,'..')) 
            continue;
    end
    folderInfo = dir(strcat('./NormalRars/', folderName));
    averageRate = 0;
    subIndex = 1;
    for j=1: 7
        fileName = folderInfo(j).name;
        if(strcmp(fileName,'.') || strcmp(fileName,'..')) 
            continue;
        end
        fileLength = folderInfo(j).bytes;       
        removedRate = (1 - fileLength / normalFilesSize(subIndex, index) ) * 100;
        ceil(removedRate);
        averageRate = averageRate + removedRate;
        subIndex = subIndex + 1;
    end
      
    normalRarRemovedRates(index) = averageRate / 5;
    index = index + 1;
end





%print Huff
disp('.....................................................................');
disp('Huff Rates');
names = '                             Cal        Can          Sil    ';
disp(names);
v = sprintf('%f  ', concatedHuffRemovedRates);
fprintf('Concated files:          %s\n', v);

v = sprintf('%f  ', fileHuffRemovedRates);
fprintf('Non concated files Files: %s\n', v);

%print RAR
disp('.....................................................................');
disp('Rar Rates');
names = '                             Cal        Can          Sil    ';
disp(names);
v = sprintf('%f  ', concatedRarRemovedRates);
fprintf('Concated files:          %s\n', v);

v = sprintf('%f  ', normalRarRemovedRates);
fprintf('Non concated files Files: %s\n', v);



end

