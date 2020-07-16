import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrmvahSharedModule } from 'app/shared/shared.module';
import { CourrierComponent } from './courrier.component';
import { CourrierDetailComponent } from './courrier-detail.component';
import { CourrierUpdateComponent } from './courrier-update.component';
import { CourrierDeleteDialogComponent } from './courrier-delete-dialog.component';
import { courrierRoute } from './courrier.route';

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(courrierRoute)],
  declarations: [CourrierComponent, CourrierDetailComponent, CourrierUpdateComponent, CourrierDeleteDialogComponent],
  entryComponents: [CourrierDeleteDialogComponent],
})
export class OrmvahCourrierModule {}
