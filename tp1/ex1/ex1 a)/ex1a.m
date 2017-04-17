function ex1()

close all

MyFolderInfo = dir('../TestFilesSM');

for i = 1 : length(MyFolderInfo)
    string = MyFolderInfo(i).name;
        if(strcmp(string,'.') || strcmp(string,'..')) 
            continue;
        end
    figure; histFile(string);
end