function ex2b()

close all

mainFolderInfo = dir('../testFiles');

nrOfFiles = 15;
entropias = zeros(1,nrOfFiles);
removedRates = zeros(1,nrOfFiles);
filesInitSize = zeros(1,nrOfFiles);
fileNumber = 1;

for i = 1 : length(mainFolderInfo)
    folderName = mainFolderInfo(i).name;
    if(strcmp(folderName,'.') || strcmp(folderName,'..')) 
        continue;
    end
    folderInfo = dir(strcat('../testFiles/', folderName));
    for j=1: 7
        fileName = folderInfo(j).name;
        fileSize = folderInfo(j).bytes;
        if(strcmp(fileName,'.') || strcmp(fileName,'..')) 
            continue;
        end
        path = strcat('../testFiles/', folderName,'/' ,fileName);
        
        [H,removed_rate,~] = getVariables(path,fileName,fileSize,fileNumber);     
        entropias(fileNumber) = H;
        removedRates(fileNumber) = removed_rate;
		filesInitSize(fileNumber) = fileSize;
        fileNumber = fileNumber + 1;
    end       
    
end

figure
for i=1 : length(entropias)
    plot(entropias(i),removedRates(i),'.');
    hold on;
end

mainFolderInfo = dir('./rars');
fileNumber = 1;
for i = 1 : length(mainFolderInfo)
    folderName = mainFolderInfo(i).name;
    if(strcmp(folderName,'.') || strcmp(folderName,'..')) 
        continue;
    end
    folderInfo = dir(strcat('./rars/', folderName));
    for j=1: 7
        fileName = folderInfo(j).name;
        fileSize = folderInfo(j).bytes;
        if(strcmp(fileName,'.') || strcmp(fileName,'..')) 
            continue;
        end
        
        [removed_rate] = rarsFunction(fileSize,fileNumber,filesInitSize);
        removedRates(fileNumber) = removed_rate;
        fileNumber = fileNumber + 1;
    end       
    
end

figure
    for i=1 : length(entropias)
        plot(entropias(i),removedRates(i),'.');
        hold on;
    end
    