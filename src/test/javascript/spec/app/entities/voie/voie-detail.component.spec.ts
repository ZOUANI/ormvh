import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { VoieDetailComponent } from 'app/entities/voie/voie-detail.component';
import { Voie } from 'app/shared/model/voie.model';

describe('Component Tests', () => {
  describe('Voie Management Detail Component', () => {
    let comp: VoieDetailComponent;
    let fixture: ComponentFixture<VoieDetailComponent>;
    const route = ({ data: of({ voie: new Voie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [VoieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(VoieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VoieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load voie on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.voie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
