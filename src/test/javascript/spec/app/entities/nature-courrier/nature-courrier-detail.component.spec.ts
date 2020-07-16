import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { NatureCourrierDetailComponent } from 'app/entities/nature-courrier/nature-courrier-detail.component';
import { NatureCourrier } from 'app/shared/model/nature-courrier.model';

describe('Component Tests', () => {
  describe('NatureCourrier Management Detail Component', () => {
    let comp: NatureCourrierDetailComponent;
    let fixture: ComponentFixture<NatureCourrierDetailComponent>;
    const route = ({ data: of({ natureCourrier: new NatureCourrier(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [NatureCourrierDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(NatureCourrierDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NatureCourrierDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load natureCourrier on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.natureCourrier).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
