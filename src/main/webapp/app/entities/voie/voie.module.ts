import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { OrmvahSharedModule } from 'app/shared/shared.module';
import { VoieComponent } from './voie.component';
import { VoieDetailComponent } from './voie-detail.component';
import { VoieUpdateComponent } from './voie-update.component';
import { VoieDeleteDialogComponent } from './voie-delete-dialog.component';
import { voieRoute } from './voie.route';

@NgModule({
  imports: [OrmvahSharedModule, RouterModule.forChild(voieRoute)],
  declarations: [VoieComponent, VoieDetailComponent, VoieUpdateComponent, VoieDeleteDialogComponent],
  entryComponents: [VoieDeleteDialogComponent],
})
export class OrmvahVoieModule {}
