import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrmvahSharedModule } from 'app/shared/shared.module';
import { BordereauComponent } from './bordereau.component';
import { BordereauDetailComponent } from './bordereau-detail.component';
import { BordereauUpdateComponent } from './bordereau-update.component';
import { BordereauDeleteDialogComponent } from './bordereau-delete-dialog.component';
import { bordereauRoute } from './bordereau.route';

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(bordereauRoute)],
  declarations: [BordereauComponent, BordereauDetailComponent, BordereauUpdateComponent, BordereauDeleteDialogComponent],
  entryComponents: [BordereauDeleteDialogComponent],
})
export class OrmvahBordereauModule {}
