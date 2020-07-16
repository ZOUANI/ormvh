import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrmvahSharedModule } from 'app/shared/shared.module';
import { CourrierObjectComponent } from './courrier-object.component';
import { CourrierObjectDetailComponent } from './courrier-object-detail.component';
import { CourrierObjectUpdateComponent } from './courrier-object-update.component';
import { CourrierObjectDeleteDialogComponent } from './courrier-object-delete-dialog.component';
import { courrierObjectRoute } from './courrier-object.route';

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(courrierObjectRoute)],
  declarations: [
    CourrierObjectComponent,
    CourrierObjectDetailComponent,
    CourrierObjectUpdateComponent,
    CourrierObjectDeleteDialogComponent,
  ],
  entryComponents: [CourrierObjectDeleteDialogComponent],
})
export class OrmvahCourrierObjectModule {}
