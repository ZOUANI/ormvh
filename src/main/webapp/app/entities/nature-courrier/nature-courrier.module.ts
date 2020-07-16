import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrmvahSharedModule } from 'app/shared/shared.module';
import { NatureCourrierComponent } from './nature-courrier.component';
import { NatureCourrierDetailComponent } from './nature-courrier-detail.component';
import { NatureCourrierUpdateComponent } from './nature-courrier-update.component';
import { NatureCourrierDeleteDialogComponent } from './nature-courrier-delete-dialog.component';
import { natureCourrierRoute } from './nature-courrier.route';

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(natureCourrierRoute)],
  declarations: [
    NatureCourrierComponent,
    NatureCourrierDetailComponent,
    NatureCourrierUpdateComponent,
    NatureCourrierDeleteDialogComponent,
  ],
  entryComponents: [NatureCourrierDeleteDialogComponent],
})
export class OrmvahNatureCourrierModule {}
