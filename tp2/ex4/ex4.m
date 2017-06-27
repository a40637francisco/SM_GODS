function ex4()

 disp('1 - Convert video');
 disp('2 - Extract images');
 i = input('Option:','s');
 decideAction(i);
end

function decideAction(i)
switch i
    case '1'
        file = chooseFile();
        format = input('Format:', 's');
        convertVideo(file, format);    
    case '2'       
        file = chooseFile();
        beginTime = input('Being time:','s');
        finalTime = input('Final time:','s');
        extractImages(file, beginTime, finalTime);   
    otherwise
        disp('Invalid index');
end        
end

function fileName = chooseFile()

disp('1 - Local file'); 
disp('2 - Youtube');
i = input('Option:','s');
switch i
    case '1'
       fileName = input('File:','s');
       fileName = ['.\InputVideo\' fileName];
    case '2'
       fileName = youtubeGetter();     
    otherwise
        disp(['Option' i ' not valid']);
        fileName = -1;
end 
return;
end

function Name = youtubeGetter()
   link  = input('Link:','s');       
   exe = ['.\DownloadVideo\youtube-dl.exe ' link ];   
   system(exe);
   outputFolder = dir('.\');
   
   for i = 1 : length(outputFolder)
        fileName = outputFolder(i).name; 
            if(strcmp(fileName,'.') || strcmp(fileName,'..') || strcmp(fileName,'InputVideo')) 
                continue;
            end
            if(strcmp(fileName,'ConvertVideo') || strcmp(fileName,'DownloadVideo') || strcmp(fileName, 'Frames')) 
                continue;   
            end
            if(strcmp(fileName,'ex4.asv') || strcmp(fileName,'ex4.m') || strcmp(fileName,'Outputs')) 
                continue;   
            end
          Name = fileName; 
          Name = strrep(Name, ' ', '');
          if(strcmp(fileName, Name) == 0)
            movefile(fileName, Name);
          end
         
   end   


end

function convertVideo(file, format)
   outFile = input('Output file name:' , 's');
   exe = ['.\ConvertVideo\ffmpeg.exe -i ' file ' .\Outputs\' outFile '.' format ];   
   system(exe);
   if exist(file, 'file')==2
    delete(file);
   end
end

%time in seconds
function extractImages(file, beginTime, finalTime)
rmdir('.\Frames', 's');
mkdir Frames
video = VideoReader(file);
video.CurrentTime =  str2double(beginTime);
finalTime = str2double(finalTime);
index = 1;
while video.CurrentTime < finalTime;
    imgName = ['.\Frames\frame' num2str(index) '.jpg'];
    frame = readFrame(video);
    imwrite(frame, imgName);
    index = index + 1;
end
if exist(file, 'file')==2
    delete(file);
end
end
%https://youtu.be/RtBbinpK5XI
