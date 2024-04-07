# Learn about building .NET container images:
# https://github.com/dotnet/dotnet-docker/blob/main/samples/README.md
FROM mcr.microsoft.com/dotnet/sdk:7.0 AS build

WORKDIR /source

# copy csproj and restore as distinct layers
WORKDIR /app

WORKDIR /src

COPY ["WebStore/WebStore.csproj", "WebStore/"]
RUN dotnet restore "WebStore/WebStore.csproj"

# copy everything else and build app
COPY . .
WORKDIR /src/WebStore/
RUN dotnet build "WebStore.csproj" -c Release -o /app/build

FROM build AS publish
RUN dotnet publish "WebStore.csproj" -c Release -o /app/publish

FROM mcr.microsoft.com/dotnet/aspnet:7.0
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "WebStore.dll"]
