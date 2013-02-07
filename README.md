protobuf-groove
===============

Experimental lib for creating protobuf classes for different languages using groovy templating engine.

How it works:
1. Use Google's _protoc_ tool to create a descriptor file out of the proto file.
   protoc --descriptor_set_out=DESCRIPTOR_FILE_NAME PROTO_FILES

2. Write a template written in groovy that operates on protobuf's "DescriptorProtos.FileDescriptorSet" object,
which is given to the groovy templating engine under name "desc".

3. Run the protobuf-groove app and pass it the input descriptor file and desired template file and watch it do its magic.


java -jar protobuf-groove.jar -Dt=template.groovy -Dpb=descriptor.pb -Dout=out.txt
