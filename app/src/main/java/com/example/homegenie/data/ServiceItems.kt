package com.example.homegenie.data

import com.example.homegenie.models.SalonService

data class ServiceItem(
    val serviceName: String,
    val ratings: Double,
    val urls: List<String>
)

val serviceItems = listOf<ServiceItem>(
    ServiceItem(
        serviceName = "Beauty & Spa",
        ratings = 4.5,
        urls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fs1.jpg?alt=media&token=2f409028-5505-4dc9-afbe-4ccc068cb5f1",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fs2.jpg?alt=media&token=5610c395-3206-48e4-91a4-7aa94134beb6",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fs3.jpg?alt=media&token=0e0349e2-7ca9-46b1-9044-13e28fd0c43e",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fs4.jpg?alt=media&token=ba00b854-7c9d-40bd-9b3c-4c8462e40bb6",
        )
    ),
    ServiceItem(
        serviceName = "Plumber",
        ratings = 4.3,
        urls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fp1.jpg?alt=media&token=12c66338-2515-4445-abad-4b21a45173e5",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fp2.jpg?alt=media&token=3eaaac06-41fa-4fa2-93a2-e4d4d291fc5a",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fp3.jpg?alt=media&token=399d3788-5c1e-4cf3-8072-755b3610c58b",
        )),
    ServiceItem(
        serviceName = "Home Cleaning",
        ratings = 4.7,
        urls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fh1.jpg?alt=media&token=9e9144cb-7ec7-4af1-9400-085d8cecf9e3",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fh2.jpg?alt=media&token=39801233-7de3-4630-b060-6ab76e5dcc4e",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fh3.jpg?alt=media&token=1260b76b-f7ea-4137-9ce0-7276e58a1653",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fh4.jpg?alt=media&token=3057ebbf-9c3d-4282-b2c4-5590d7425cae",
        )
    ),
    ServiceItem(
        serviceName = "Carpenter",
        ratings = 4.1,
        urls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fc1.jpg?alt=media&token=2fe06bc7-7ab5-4fa1-9671-3a06f5249c3a",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fc2.jpg?alt=media&token=9185c9a4-6f7d-42bc-a22a-7fdc590ebb79",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fc3.jpg?alt=media&token=f0f6d43a-d4a1-4ced-88eb-c10e2b81e8bf",
        )
    ),
    ServiceItem(
        serviceName = "Electrician",
        ratings = 4.2,
        urls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fe1.jpg?alt=media&token=a7397273-00d5-4746-a050-de1078171d33",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fe2.jpg?alt=media&token=16fbd1f6-2d38-4e4e-b63b-a1f505b4bcd8",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fe3.jpg?alt=media&token=0b58b9dd-3d57-4895-8d2f-a06ba760f7d2",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fe4.jpg?alt=media&token=04a5ce52-9031-476f-b5a9-a0cae3487c07",
        )
    ),
    ServiceItem(
        serviceName = "Gardener",
        ratings = 4.3,
        urls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fg1.jpg?alt=media&token=288f18b5-eaa4-4ffd-a2a9-2a66115e24a5",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fg2.jpg?alt=media&token=44f5812d-37a3-44a3-b019-19a4580c5742",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fg3.jpg?alt=media&token=c4a9d3f1-23bb-40f1-9b57-1f118cfc8e0b",
        )
    ),
    ServiceItem(
        serviceName = "Painter",
        ratings = 4.2,
        urls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fw1.jpg?alt=media&token=5e423501-d4d9-4f8a-a4c2-f76e70f07614",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fw2.jpg?alt=media&token=3bb82bd6-0952-4dfa-a1a9-03010461d6e5",
        )
    ),
    ServiceItem(
        serviceName = "Car Mechanic",
        ratings = 4.4,
        urls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fm1.jpg?alt=media&token=cd685440-8635-47fc-9d82-278b9474d687",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services_lite%2Fm2.jpg?alt=media&token=aaccacba-71d4-42be-bdb4-cd5868afa155",
        )
    ),
)

/*
val serviceItems = listOf<ServiceItem>(
    ServiceItem(
        serviceName = "Beauty & Spa",
        ratings = 4.5,
        urls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fsalon%2Fs1.jpg?alt=media&token=ae59095f-6ce5-4a6b-9ab9-dea56cab5e4d",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fsalon%2Fs2.jpg?alt=media&token=3d667c45-f704-4293-9103-25172b21514f",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fsalon%2Fs3.jpg?alt=media&token=b26ec76e-d62c-4215-afea-6e9b1ed2e935",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fsalon%2Fs4.jpg?alt=media&token=6667e5eb-6d56-4a6b-a80d-3d34f53a7523"
        )
    ),
    ServiceItem(
        serviceName = "Plumber",
        ratings = 4.3,
        urls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fplumber%2Fp1.jpg?alt=media&token=73a7854f-98e5-4333-b683-e3f67e29b7f0",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fplumber%2Fp2.jpg?alt=media&token=3aaf349f-c2af-4306-bca9-636759638156",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fplumber%2Fp3.jpg?alt=media&token=2dedf1f2-45f1-446a-ac6a-92b8b7180425"
        )),
    ServiceItem(
        serviceName = "Home Cleaning",
        ratings = 4.7,
        urls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fhousekeeping%2Fh1.jpg?alt=media&token=0a509407-8efe-4ce4-b18d-76ce69ff34ee",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fhousekeeping%2Fh2.jpg?alt=media&token=5b3eb4b6-3496-4efd-95da-ec408a38d52c",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fhousekeeping%2Fh3.jpg?alt=media&token=892350ed-87ec-47e7-81ed-3c71e4eea594",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fhousekeeping%2Fh4.jpg?alt=media&token=d9849878-f589-4c38-9204-c1d6728b080e"
        )
    ),
    ServiceItem(
        serviceName = "Carpenter",
        ratings = 4.1,
        urls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fcarpenter%2Fc1.jpg?alt=media&token=8b4853bd-ca15-46d3-b37d-f0aa458af17e",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fcarpenter%2Fc2.jpg?alt=media&token=49ee9f96-75e5-463b-967e-d778cca26111",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fcarpenter%2Fc3.jpg?alt=media&token=efb965a1-7b2f-4075-9bdd-f3f9e5f0fda5"
        )
    ),
    ServiceItem(
        serviceName = "Electrician",
        ratings = 4.2,
        urls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Felectrician%2Fe1.jpg?alt=media&token=b74d89c4-aff4-4064-86b7-c7e55c61fc75",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Felectrician%2Fe2.jpg?alt=media&token=345b11aa-5c38-4bba-bfd0-99a963914104",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Felectrician%2Fe3.jpg?alt=media&token=4269e1c7-8350-4dac-9d17-cc6c52220112",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Felectrician%2Fe4.jpg?alt=media&token=22a8e73a-c49e-4407-9689-bee83b81e7a8"
        )
    ),
    ServiceItem(
        serviceName = "Gardener",
        ratings = 4.3,
        urls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fgardener%2Fg1.jpg?alt=media&token=d42f8487-f5fa-45f5-8db8-5c8c8071d094",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fgardener%2Fg2.jpg?alt=media&token=ea86005a-1259-4a5b-96d6-fbbdb8a16c5b",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fgardener%2Fg3.jpg?alt=media&token=7a2333e1-e218-4a00-a43b-fc003b177387"
        )
    ),
    ServiceItem(
        serviceName = "Painter",
        ratings = 4.2,
        urls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fpainter%2Fw1.jpg?alt=media&token=83173318-b774-45c3-9a3d-02e72cf19f7b",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fpainter%2Fw2.jpg?alt=media&token=7ac36942-5d36-4a3b-a66e-e284c2df15ba"
        )
    ),
    ServiceItem(
        serviceName = "Car Mechanic",
        ratings = 4.4,
        urls = listOf(
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fmechanic%2Fm1.jpg?alt=media&token=6a0f08ee-29b3-441e-8bb5-8e2c7d8c639d",
            "https://firebasestorage.googleapis.com/v0/b/home-genie-d8894.appspot.com/o/services%2Fmechanic%2Fm2.jpg?alt=media&token=388f0446-357b-409a-804f-9b177b38a19c"
        )
    ),
)
 */

