function ex2a()

close all

mainFolderInfo = dir('../testFiles');

for i = 1 : length(mainFolderInfo)
    folderName = mainFolderInfo(i).name;
    if(strcmp(folderName,'.') || strcmp(folderName,'..')) 
        continue;
    end
    folderInfo = dir(strcat('../testFiles/', folderName));
    for j=1: 7
        fileName = folderInfo(j).name;
        if(strcmp(fileName,'.') || strcmp(fileName,'..')) 
            continue;
        end
        path = strcat('../testFiles/', folderName,'/' ,fileName);
        figure; histFile(path, fileName, 256);
    end       
    
end